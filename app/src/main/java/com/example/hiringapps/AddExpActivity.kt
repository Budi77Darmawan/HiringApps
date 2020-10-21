package com.example.hiringapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.hiringapps.databinding.ActivityAddExpBinding
import kotlinx.android.synthetic.main.toolbar_add.view.*

class AddExpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddExpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_exp)

        binding.inc.tv_toolbar_add.text = "Tambah Pekerjaan"

        binding.inc.tv_save.setOnClickListener(this)
        binding.inc.img_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_save -> {
                finish()
            }
            R.id.img_back -> {
                onBackPressed()
            }
        }
    }

}
