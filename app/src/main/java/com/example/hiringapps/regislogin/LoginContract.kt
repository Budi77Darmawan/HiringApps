package com.example.hiringapps.regislogin

import android.content.Context

interface LoginContract {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
        fun sharedPreferenced(shared: Boolean)
        fun response(res: String?)
        fun intent(intent: Boolean)
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun sharedPreferenced(mContext: Context, remember: Boolean)
        fun callAuthApi(email: String?, password: String?)
    }
}