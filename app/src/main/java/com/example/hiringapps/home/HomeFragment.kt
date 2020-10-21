package com.example.hiringapps.home

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.freelancers.FreelancersApiService
import com.example.hiringapps.api.freelancers.FreelancersResponse
import com.example.hiringapps.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottomsheet_home.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bottomsheetHome: BottomSheetDialog

    private var list = listOf<DetailFreelancersModel>()
    private var search = ""
    private var job = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.rvApp.setHasFixedSize(true)

        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)

        getFreelancersApi(search, job)

        bottomsheetHome = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottomsheet_home, null)
        bottomsheetHome.setContentView(view)

        view.btn_filter.setOnClickListener(this)
        view.android.setOnClickListener(this)
        view.ios.setOnClickListener(this)
        view.web.setOnClickListener(this)

        return binding.root
    }

    private fun getFreelancersApi(search: String, job: String) {
        val service =
            ApiClient.getApiClient(requireContext())?.create(FreelancersApiService::class.java)

        service?.getFreelancersRequest(search, job)
            ?.enqueue(object : Callback<FreelancersResponse> {
                override fun onFailure(call: Call<FreelancersResponse>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Check your internet connection",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<FreelancersResponse>,
                    response: Response<FreelancersResponse>
                ) {
                    list = response.body()?.data?.map {
                        DetailFreelancersModel(
                            it.id_freelancer.orEmpty(),
                            it.name.orEmpty(),
                            it.job.orEmpty(),
                            it.status.orEmpty(),
                            it.city.orEmpty(),
                            it.description.orEmpty(),
                            it.skill.orEmpty(),
                            it.image.orEmpty()
                        )
                    } ?: listOf()
                    if (list.isNotEmpty()) {
                        binding.tvTip.visibility = View.GONE
                    } else {
                        binding.tvTip.visibility = View.VISIBLE
                    }
                    showRecyclerGrid(list)

                }
            })
    }

    private fun showRecyclerGrid(list: List<DetailFreelancersModel>) {
        val listFreelancersAdapter = GridFreelancersAdapter(list)
        binding.rvApp.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvApp.adapter = listFreelancersAdapter

        listFreelancersAdapter.setOnItemClickCallback(object :
            GridFreelancersAdapter.OnItemClickCallback {

            override fun onItemClicked(id: Int) {
                val intent = Intent(activity, DetailFreelancersActivity::class.java)
                intent.putExtra(DetailFreelancersActivity.DATA_FREELANCERS, list[id])
                requireActivity().startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_hint)

        if (menuItem != null) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        search = newText.toLowerCase(Locale.getDefault())
                        getFreelancersApi(search, job)
                        rv_app.adapter!!.notifyDataSetChanged()
                    } else {
                        search = ""
                        getFreelancersApi(search, job)
                        rv_app.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
                bottomsheetHome.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        val view = layoutInflater.inflate(R.layout.bottomsheet_home, null)
        when (v?.id) {
            R.id.android -> {
//                Log.d("ANDROID1", "ANDROID")
                if (job != "Android Developer") {
                    job = "Android Developer"
                    view.android.isSelected = !view.android.isSelected
                }
            }
            R.id.btn_filter -> {
                getFreelancersApi(search, job)
                bottomsheetHome.dismiss()
            }
        }
    }

}
