package com.example.hiringapps.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.freelancers.FreelancersApiService
import com.example.hiringapps.api.freelancers.FreelancersResponse
import com.example.hiringapps.databinding.FragmentDetailBinding
import kotlinx.coroutines.*

class DetailFragment(private val idFreelancer: String?) : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private var list = listOf<FreelancersModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        getDetailApi()

        return binding.root
    }

    private fun getDetailApi() {
        val service =
            ApiClient.getApiClient(requireContext())?.create(FreelancersApiService::class.java)
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getFreelancersbyIDRequest(idFreelancer)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is FreelancersResponse) {
                list = response.data?.map {
                    FreelancersModel(
                        it.id_freelancer.orEmpty(),
                        it.name.orEmpty(),
                        it.job.orEmpty(),
                        it.status.orEmpty(),
                        it.city.orEmpty(),
                        it.description.orEmpty(),
                        it.skill.orEmpty(),
                        it.image.orEmpty(),
                        it.email.orEmpty(),
                        it.numberPhone.orEmpty()
                    )
                } ?: listOf()

                binding.apply {
                    binding.tvDesc.text = response.data?.get(0)?.description
                    binding.tvSkill.text = response.data?.get(0)?.skill
                    binding.tvNumberphone.text = response.data?.get(0)?.numberPhone
                    binding.tvEmail.text = response.data?.get(0)?.email
                }
            }
        }
    }
}