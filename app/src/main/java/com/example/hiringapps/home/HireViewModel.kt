package com.example.hiringapps.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiringapps.api.hireproject.HireApiService
import com.example.hiringapps.api.hireproject.HireResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HireViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: HireApiService
    var messageLiveData = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setHireService(service: HireApiService) {
        this.service = service
    }

    fun createHireApi(idProject: String, idFree: String?, message: String, projectJob: String, price: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.posthireproject(idProject, idFree, message, projectJob, price)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is HireResponse) {
                messageLiveData.value = response.message
            }
        }
    }
}