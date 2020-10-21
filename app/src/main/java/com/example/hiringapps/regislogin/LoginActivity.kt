package com.example.hiringapps.regislogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.hiringapps.MainActivity
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.auth.AuthApiService
import com.example.hiringapps.databinding.ActivityLoginBinding
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity(), LoginContract.View, View.OnClickListener{

    private lateinit var binding: ActivityLoginBinding
    private lateinit var coroutineScope: CoroutineScope
    private var presenter: LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btnLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)

        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        presenter = LoginPresenter(coroutineScope, service)
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        presenter = null
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_login -> {
                binding.progressBar.visibility = View.VISIBLE
                val email = binding.edtEmail.text.toString().trim()
                val password = binding.edtPassword.text.toString().trim()
                val check = check(email, password)
                if (check) {
                    presenter?.callAuthApi(email, password)
                }
            }

            R.id.tv_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun check (email: String?, password: String?): Boolean {
        if (email.isNullOrEmpty()) {
            binding.edtEmail.error = "Enter Email"
            binding.edtEmail.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Invalid Email"
            binding.edtEmail.requestFocus()
            return false
        }
        if (password.isNullOrEmpty()) {
            binding.edtPassword.error = "Enter Password"
            binding.edtPassword.requestFocus()
            return false
        }
        return true
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun sharedPreferenced(shared: Boolean) {
        if (shared) {
            presenter?.sharedPreferenced(this, binding.cbRemember.isChecked)
        }
    }

    override fun response(res: String?) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
    }

    override fun intent(intent: Boolean) {
        if (intent) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
