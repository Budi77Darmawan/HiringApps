package com.example.hiringapps.project

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiringapps.api.project.DeleteProjectResponse
import com.example.hiringapps.api.project.ProjectApiService
import com.example.hiringapps.api.project.ProjectResponse
import com.example.hiringapps.sharedpref.Constant
import com.example.hiringapps.sharedpref.SharedPrefProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProjectViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: ProjectApiService
    private lateinit var sharedPref: SharedPrefProvider
    var listLiveData = MutableLiveData(listOf<ProjectModel>())
    val idAccountLiveData = MutableLiveData<String>()
    val messageLiveData = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun getSharedPreference(mContext: Context) {
        sharedPref = SharedPrefProvider(mContext)
        idAccountLiveData.value = sharedPref.getString(Constant.KEY_ACCOUNT)
    }

    fun setProjectService(service: ProjectApiService) {
        this.service = service
    }

    fun getProjectApi(idAccount: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getProjectbyIDRequest(idAccount)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is ProjectResponse) {
                listLiveData.value = response.data?.map {
                    ProjectModel(
                        it.id_project.orEmpty(),
                        it.name_project.orEmpty(),
                        it.deadline.orEmpty(),
                        it.description.orEmpty(),
                        it.image.orEmpty()
                    )
                }
            }
        }
    }

    fun deleteProjectApi(idProject: Int?) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.deleteProject(idProject)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is DeleteProjectResponse) {
                messageLiveData.value = response.message
            }
        }
    }
}
