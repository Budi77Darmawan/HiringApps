package com.example.hiringapps.offers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiringapps.api.hireproject.HireApiService
import com.example.hiringapps.api.hireproject.HireListResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OffersViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: HireApiService
    var listLiveData = MutableLiveData(listOf<OffersModel>())
    val messageLiveData = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setProjectService(service: HireApiService) {
        this.service = service
    }

    fun getListHireProjectApi() {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getlisthireproject()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is HireListResponse) {
                listLiveData.value = response.data?.map {
                    OffersModel(
                        it.id_project.orEmpty(),
                        it.name.orEmpty(),
                        it.project_name.orEmpty(),
                        it.job.orEmpty(),
                        it.message.orEmpty(),
                        it.price.orEmpty(),
                        it.status.orEmpty(),
                        it.image.orEmpty()
                    )
                }
            }
        }
    }
}
