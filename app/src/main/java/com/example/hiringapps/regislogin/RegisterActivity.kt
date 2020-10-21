package com.example.hiringapps.regislogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.account.AccountApiService
import com.example.hiringapps.api.account.RegisterResponse
import com.example.hiringapps.databinding.ActivityRegisterBinding
import com.example.hiringapps.sharedpref.SharedPrefProvider
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPref: SharedPrefProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        sharedPref = SharedPrefProvider(applicationContext)
        binding.tvSignin2.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_register -> {
                binding.progressBar.visibility = View.VISIBLE
                val name = binding.edtRegname.text.toString().trim()
                val email = binding.edtRegemail.text.toString().trim()
                val companyName = binding.edtRegcompanyname.text.toString().trim()
                val position = binding.edtRegposition.text.toString().trim()
                val numberPhone = binding.edtRegnumberphone.text.toString().trim()
                val password = binding.edtRegpassword.text.toString().trim()
                val rePassword = binding.edtRegconfirmpass.text.toString().trim()
                val validate = checkData(name, email, companyName, position, numberPhone, password, rePassword)
                if (validate) {
                    registerApi(name, email, companyName, position, numberPhone, password)
                }
            }
            R.id.tv_signin2 -> {
                finish()
            }
        }
    }

    private fun checkData(name: String?, email: String?, companyName: String?, position: String?, numberPhone: String?, password: String?, repassword: String?): Boolean {
        if (name.isNullOrEmpty()) {
            binding.edtRegemail.error = "Enter Name"
            binding.edtRegemail.requestFocus()
            return false
        }
        if (email.isNullOrEmpty()) {
            binding.edtRegemail.error = "Enter Email"
            binding.edtRegemail.requestFocus()
            return false
        }
        if (companyName.isNullOrEmpty()) {
            binding.edtRegcompanyname.error = "Enter Company Name"
            binding.edtRegcompanyname.requestFocus()
            return false
        }
        if (position.isNullOrEmpty()) {
            binding.edtRegposition.error = "Enter Position"
            binding.edtRegposition.requestFocus()
            return false
        }
        if (numberPhone.isNullOrEmpty()) {
            binding.edtRegnumberphone.error = "Enter Number Phone"
            binding.edtRegnumberphone.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtRegemail.error = "Invalid Email"
            binding.edtRegemail.requestFocus()
            return false
        }
        if (password.isNullOrEmpty()) {
            binding.edtRegpassword.error = "Enter Password"
            binding.edtRegpassword.requestFocus()
            return false
        }
        if (repassword.isNullOrEmpty()) {
            binding.edtRegconfirmpass.error = "Enter Re-Password"
            binding.edtRegconfirmpass.requestFocus()
            return false
        }
        if (password != repassword) {
            binding.edtRegpassword.error = "Passwords Mismatch "
            binding.edtRegconfirmpass.error = "Passwords Mismatch"
            return false
        }
        return true
    }

    private fun registerApi(name: String?, email: String?, companyName: String?, position: String?, numberPhone: String?, password: String?) {
        val service = ApiClient.getApiClient(this)?.create(AccountApiService::class.java)
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.registerRequest("Recruiters", name, email, companyName, position, numberPhone, password)
                } catch (e: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    e.printStackTrace()
                }
            }

            if (response is RegisterResponse) {
                if (response.success) {
                    Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.GONE
            }
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onBackPressed() {
        finish()
    }
}
