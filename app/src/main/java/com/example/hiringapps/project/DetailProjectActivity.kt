package com.example.hiringapps.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.hiringapps.R
import com.example.hiringapps.databinding.ActivityDetailProjectBinding
import com.example.hiringapps.profile.EditProfileActivity
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.android.synthetic.main.toolbar_add.view.*

class DetailProjectActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailProjectBinding
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_project)

        val dataProject = intent.getParcelableExtra<ProjectModel>(EXTRA_DATA)

        binding.apply {
            tvProjectName.text = dataProject?.name
            tvDeadline.text = dataProject?.deadline
            tvDesc.text = dataProject?.description
            Glide.with(this@DetailProjectActivity)
                .load("http://10.0.2.2:8080/images/${dataProject?.image}")
                .into(imgProject)
        }

        binding.inc.tv_nameToolbar.text = "Detail Project"
        binding.inc.img_backToolbar.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_back -> {
                finish()
            }
        }
    }
}