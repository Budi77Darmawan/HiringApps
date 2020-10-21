package com.example.hiringapps.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.hiringapps.regislogin.LoginActivity
import com.example.hiringapps.R
import com.example.hiringapps.databinding.ActivityOnBoardBinding
import com.example.hiringapps.sharedpref.Constant
import com.example.hiringapps.sharedpref.SharedPrefProvider
import kotlinx.android.synthetic.main.activity_on_board.*

class OnBoardActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding: ActivityOnBoardBinding
    private lateinit var sharedPref: SharedPrefProvider

    private val introSliderAdapter =
        IntroSlideAdapter(
            listOf(
                IntroSlide(
                    "Freelancers with various skills",
                    "Find expert freelancers that fit your company's needs",
                    R.drawable.onboard1
                ),
                IntroSlide(
                    "Find in short time",
                    "Find expert freelancers in your spare time as you will find them in short time",
                    R.drawable.onboard2
                ),
                IntroSlide(
                    "Discover The Best Talent with Us",
                    "Bring more kickass talent to Indonesia’s tech ecosystem",
                    R.drawable.onboard3
                )
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_on_board
        )

        sharedPref = SharedPrefProvider(applicationContext)
        binding.btnLogin1.setOnClickListener(this)
        binding.tvNext.setOnClickListener(this)

        introSliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)

        introSliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicator_container.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        if (index == 2) {
            binding.tvNext.visibility = View.GONE
            binding.btnLogin1.visibility = View.VISIBLE
        } else {
            binding.tvNext.visibility = View.VISIBLE
            binding.btnLogin1.visibility = View.GONE
        }

        val childOut = indicator_container.childCount
        for (i in 0 until childOut){
            val imageView = indicator_container[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login1 -> {
                sharedPref.putBoolean(Constant.KEY_ONBOARD, true)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.tv_next -> introSliderViewPager.currentItem += 1
        }
    }
}

