package com.example.hiringapps.offers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.hireproject.HireApiService
import com.example.hiringapps.databinding.FragmentOffersBinding
import kotlinx.android.synthetic.main.toolbar.view.*

class OffersFragment : Fragment() {
    private lateinit var binding: FragmentOffersBinding
    private lateinit var viewModel: OffersViewModel
    private lateinit var list: List<OffersModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offers, container, false)
        binding.rvOffers.setHasFixedSize(true)

        viewModel = ViewModelProvider(this).get(OffersViewModel::class.java)
        val service =
            ApiClient.getApiClient(requireContext())?.create(HireApiService::class.java)
        if (service != null) {
            viewModel.setProjectService(service)
        }
        viewModel.getListHireProjectApi()
        subscribeLiveData()

        binding.inc.tv_nameToolbar.text = "List Offers"
        binding.inc.img_backToolbar.visibility = View.GONE

        return binding.root
    }

    private fun showRecyclerList(list: List<OffersModel>) {
        binding.rvOffers.layoutManager = LinearLayoutManager(requireContext())
        val listOffersAdapter = ListOffersAdapter(list)
        binding.rvOffers.adapter = listOffersAdapter

        listOffersAdapter.setOnItemClickCallback(object :
            ListOffersAdapter.OnItemClickCallback {

            override fun onItemClicked(id: Int) {
//                val intent = Intent(activity, DetailProjectActivity::class.java)
//                intent.putExtra(DetailProjectActivity.EXTRA_DATA, list[id])
//                startActivity(intent)
            }
        })
    }

    private fun subscribeLiveData() {
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            list = it
            showRecyclerList(it)
        })
    }
}
