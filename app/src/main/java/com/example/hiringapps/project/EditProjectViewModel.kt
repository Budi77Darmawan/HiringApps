package com.example.hiringapps.project

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiringapps.api.project.ProjectApiService
import com.example.hiringapps.api.project.UpdateProjectResponse
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.coroutines.CoroutineContext

class EditProjectViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: ProjectApiService
    private lateinit var image: MultipartBody.Part
    val messageLiveData = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setProjectService(service: ProjectApiService) {
        this.service = service
    }

    fun updateProjectApi(idProject: String, name: String, deadline: String, description: String, imgFile: File?) {
        val projectName = createPartFromString(name)
        val projectDeadline = createPartFromString(deadline)
        val projectDescription = createPartFromString(description)

        if (imgFile != null) {
            val exs = imgFile.name.split(".")[1]
            val requestFile =
                imgFile.asRequestBody("image/${exs}".toMediaTypeOrNull())
            image =
                MultipartBody.Part.createFormData("image", imgFile.name, requestFile)
        }

        val partMap = HashMap<String, RequestBody>()
        partMap["name"] = projectName
        partMap["description"] = projectDescription
        partMap["deadline"] = projectDeadline

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    if (imgFile != null) {
                        service.updateProject(idProject, partMap, image)
                    } else {
                        service.updateProject2(idProject, partMap)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is UpdateProjectResponse) {
                messageLiveData.value = response.message
            }
        }
    }

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "text/plain".toMediaType()
        return json
            .toRequestBody(mediaType)
    }
}