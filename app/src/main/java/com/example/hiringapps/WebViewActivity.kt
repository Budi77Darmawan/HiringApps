package com.example.hiringapps

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.hiringapps.databinding.ActivityWebViewBinding
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        val linkUrl = intent.getStringExtra("url")

        if (linkUrl != null) {
            binding.webView.loadUrl(linkUrl)
        }

        binding.webView.webViewClient = WebViewClient()
        web_view.webViewClient = object: WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
