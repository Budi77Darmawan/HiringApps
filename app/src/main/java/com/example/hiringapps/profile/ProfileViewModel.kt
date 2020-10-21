package com.example.hiringapps.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiringapps.api.profile.ProfileApiService
import com.example.hiringapps.api.profile.ProfileResponse
import com.example.hiringapps.sharedpref.Constant
import com.example.hiringapps.sharedpref.SharedPrefProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProfileViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: ProfileApiService
    private lateinit var sharedPref: SharedPrefProvider
    val dataProfileLiveData = MutableLiveData<ProfileModel>()
    val idAccountLiveData = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun getSharedPreference(mContext: Context) {
        sharedPref = SharedPrefProvider(mContext)
        idAccountLiveData.value = sharedPref.getString(Constant.KEY_ACCOUNT)
        Log.d("IDACCOUNT", idAccountLiveData.toString())
    }

    fun setProfileService(service: ProfileApiService) {
        this.service = service
    }

    fun getProfileApi(idAccount: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getProfilebyID(idAccount)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is ProfileResponse) {
                dataProfileLiveData.value = ProfileModel(
                    response.data?.get(0)?.name,
                    response.data?.get(0)?.company,
                    response.data?.get(0)?.position,
                    response.data?.get(0)?.sector,
                    response.data?.get(0)?.city,
                    response.data?.get(0)?.description,
                    response.data?.get(0)?.website,
                    response.data?.get(0)?.image
                )
            }
        }
    }
}