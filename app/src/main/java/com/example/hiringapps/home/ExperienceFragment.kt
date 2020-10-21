package com.example.hiringapps.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.experience.ExperienceApiService
import com.example.hiringapps.api.experience.ExperienceResponse
import com.example.hiringapps.databinding.FragmentExperienceBinding
import kotlinx.coroutines.*

class ExperienceFragment(private val idFreelancer: String?) : Fragment() {
    private lateinit var binding: FragmentExperienceBinding
    private var list = listOf<ExperienceModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false)
        binding.rvExperience.setHasFixedSize(true)

        getExperienceApi()

        return binding.root
    }

    private fun getExperienceApi() {
        val service =
            ApiClient.getApiClient(requireContext())?.create(ExperienceApiService::class.java)
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getExperiencebyIDRequest(idFreelancer)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is ExperienceResponse) {
                if (response.data != null) {
                    list = response.data.map {
                        ExperienceModel(
                            it.id_account.orEmpty(),
                            it.company_name.orEmpty(),
                            it.position.orEmpty(),
                            it.start.orEmpty(),
                            it.end.orEmpty(),
                            it.description.orEmpty()
                        )
                    }
                    binding.imgBox.visibility = View.GONE
                    binding.descImg.visibility = View.GONE
                } else {
                    binding.imgBox.visibility = View.VISIBLE
                    binding.descImg.visibility = View.VISIBLE
                }
                showRecyclerList(list)

            }
        }
    }

    private fun showRecyclerList(list: List<ExperienceModel>) {
        binding.rvExperience.layoutManager = LinearLayoutManager(requireContext())
        val listExperienceAdapter = ListExperienceAdapter(list)
        binding.rvExperience.adapter = listExperienceAdapter

        listExperienceAdapter.setOnItemClickCallback(object :
            ListExperienceAdapter.OnItemClickCallback {

            override fun onItemClicked(id: Int) {
                Toast.makeText(requireContext(), list[id].job, Toast.LENGTH_SHORT).show()
//                val intent = Intent(activity, DetailProjectActivity::class.java)
//                intent.putExtra(DetailProjectActivity.EXTRA_DATA, list[id])
//                startActivity(intent)
            }
        })
    }
}