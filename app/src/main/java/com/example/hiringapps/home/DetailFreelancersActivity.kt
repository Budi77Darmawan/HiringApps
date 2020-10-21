package com.example.hiringapps.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.hiringapps.R
import com.example.hiringapps.databinding.ActivityDetailFreelancersBinding
import kotlinx.android.synthetic.main.activity_detail_freelancers.*
import kotlinx.android.synthetic.main.toolbar.view.*


class DetailFreelancersActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailFreelancersBinding
    private var dataFreelancers: DetailFreelancersModel? = null

    companion object {
        const val DATA_FREELANCERS = "data_freelancers"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_freelancers)

        dataFreelancers = intent.getParcelableExtra(DATA_FREELANCERS)

        binding.let {
            it.tvName.text = dataFreelancers?.name
            it.tvJob.text = dataFreelancers?.job
            it.tvCity.text = dataFreelancers?.city
            it.tvStatus.text = dataFreelancers?.status

            Glide.with(this)
                .load("http://10.0.2.2:8080/images/${dataFreelancers?.image}")
                .into(it.imgProfil)

            it.toolbar.tv_nameToolbar.text = "Detail Freelancers"
            it.toolbar.img_backToolbar.setOnClickListener(this)
            it.btnHire.setOnClickListener(this)
        }
        setUpTabs(dataFreelancers?.id_freelancers)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_hire -> {
                val intent = Intent(this, HireProjectActivity::class.java)
                intent.putExtra(HireProjectActivity.DATA_FREELANCERS, dataFreelancers)
                startActivity(intent)
            }
            R.id.img_backToolbar -> {
                onBackPressed()
            }
        }
    }

    private fun setUpTabs(idFreelancer: String?) {
        val adapter = ExperienceVPAdapter(supportFragmentManager)
        adapter.addFragment(DetailFragment(idFreelancer), "Detail")
        adapter.addFragment(ExperienceFragment(idFreelancer), "Experience")
        adapter.addFragment(PortofolioFragment(idFreelancer), "Portofolio")
        vp_recyclerview.adapter = adapter
        TabLayout.setupWithViewPager(vp_recyclerview)
    }
}
