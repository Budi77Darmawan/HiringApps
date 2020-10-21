package com.example.hiringapps.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.portofolio.PortofolioApiService
import com.example.hiringapps.api.portofolio.PortofolioResponse
import com.example.hiringapps.databinding.FragmentPortofolioBinding
import kotlinx.coroutines.*

class PortofolioFragment(private val idFreelancer: String?) : Fragment() {
    private lateinit var binding: FragmentPortofolioBinding
    private var list = listOf<PortofolioModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_portofolio, container, false)
        binding.rvPortofolio.setHasFixedSize(true)

        getPortofolioApi()

        return binding.root
    }

    private fun getPortofolioApi() {
        val service =
            ApiClient.getApiClient(requireContext())?.create(PortofolioApiService::class.java)
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getPortofoliobyIDRequest(idFreelancer)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is PortofolioResponse) {
                if (response.data != null) {
                    list = response.data.map {
                        PortofolioModel(
                            it.id_portofolio.orEmpty(),
                            it.id_account.orEmpty(),
                            it.name.orEmpty(),
                            it.image.orEmpty(),
                            it.description.orEmpty(),
                            it.link_repo.orEmpty(),
                            it.type_portofolio.orEmpty()
                        )
                    }
                    binding.imgBox.visibility = View.GONE
                    binding.descImg.visibility = View.GONE
                } else {
                    binding.imgBox.visibility = View.VISIBLE
                    binding.descImg.visibility = View.VISIBLE
                }
                showRecyclerGrid(list)

            }
        }
    }

    private fun showRecyclerGrid(list: List<PortofolioModel>) {
        val listPortofolioAdapter = GridPortofolioAdapter(list)
        binding.rvPortofolio.layoutManager = GridLayoutManager(context, 2)
        binding.rvPortofolio.adapter = listPortofolioAdapter

        listPortofolioAdapter.setOnItemClickCallback(object :
            GridPortofolioAdapter.OnItemClickCallback {

            override fun onItemClicked(id: Int) {
                Toast.makeText(context, list[id].name, Toast.LENGTH_SHORT).show()
//                val intent = Intent(activity, DetailFreelancersActivity::class.java)
//                intent.putExtra(DetailFreelancersActivity.DATA_FREELANCERS, list[id])
//                requireActivity().startActivity(intent)
            }
        })
    }
}