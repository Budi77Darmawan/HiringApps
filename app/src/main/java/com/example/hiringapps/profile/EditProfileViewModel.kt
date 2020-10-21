package com.example.hiringapps.profile

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiringapps.api.account.AccountApiService
import com.example.hiringapps.api.account.UpdateRecruitersResponse
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.coroutines.CoroutineContext

class EditProfileViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: AccountApiService
    private lateinit var image: MultipartBody.Part
    val messageLiveData = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setAccountService(service: AccountApiService) {
        this.service = service
    }

    fun updateAccountApi(name: String) {
        launch {
            try {
                service.updateAccountRequest(name)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun updateRecruiterApi(company: String, position: String, city: String, desc: String, imgFile: File?) {
        val companyRec = createPartFromString(company)
        val positionRec = createPartFromString(position)
        val cityRec = createPartFromString(city)
        val descRec = createPartFromString(desc)

        val partMap = HashMap<String, RequestBody>()
        partMap["companyName"] = companyRec
        partMap["position"] = positionRec
        partMap["city"] = cityRec
        partMap["description"] = descRec

        if (imgFile != null) {
            val exs = imgFile.name.split(".")[1]
            val requestFile =
                imgFile.asRequestBody("image/${exs}".toMediaTypeOrNull())
            image =
                MultipartBody.Part.createFormData("image", imgFile.name, requestFile)
        }
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    if (imgFile != null) {
                        service.updateRecruitersRequest(partMap, image)
                    } else {
                        service.updateRecruiters2Request(partMap)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is UpdateRecruitersResponse) {
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