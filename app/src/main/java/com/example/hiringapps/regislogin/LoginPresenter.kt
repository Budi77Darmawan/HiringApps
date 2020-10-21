package com.example.hiringapps.regislogin

import android.content.Context
import com.example.hiringapps.api.auth.AuthApiService
import com.example.hiringapps.api.auth.LoginResponse
import com.example.hiringapps.sharedpref.Constant
import com.example.hiringapps.sharedpref.SharedPrefProvider
import kotlinx.coroutines.*

class LoginPresenter(
    private val coroutineScope: CoroutineScope,
    private val service: AuthApiService?
) : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private lateinit var sharedPref: SharedPrefProvider
    private lateinit var idAccount: String
    private lateinit var token: String

    override fun bindToView(view: LoginContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun sharedPreferenced(mContext: Context, remember: Boolean) {
        sharedPref = SharedPrefProvider(mContext)
        sharedPref.putString(Constant.KEY_ACCOUNT, idAccount)
        sharedPref.putString(Constant.KEY_TOKEN, token)
        sharedPref.putBoolean(Constant.KEY_REMEMBER, remember)
    }

    override fun callAuthApi(email: String?, password: String?) {
        coroutineScope.launch {
            view?.showProgressBar()
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.loginRequest(email, password)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is LoginResponse) {
                if (response.data?.role == "Recruiters") {
                    if (response.data.status == "1") {
                        idAccount = response.data.id.toString()
                        token = response.data.token.toString()
                        view?.sharedPreferenced(true)
                        view?.hideProgressBar()
                        view?.intent(true)
                    } else {
                        view?.hideProgressBar()
                        view?.response("Unverified account")
                    }
                } else if (response.data?.role == "Freelancers") {
                    view?.hideProgressBar()
                    view?.response("You can't acces this")
                } else {
                    view?.hideProgressBar()
                    view?.response(response.message)
                }
            }
        }
    }
}