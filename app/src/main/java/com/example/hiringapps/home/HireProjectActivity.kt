package com.example.hiringapps.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.hireproject.HireApiService
import com.example.hiringapps.api.project.ProjectApiService
import com.example.hiringapps.databinding.ActivityHireProjectBinding
import com.example.hiringapps.project.ProjectModel
import com.example.hiringapps.project.ProjectViewModel
import kotlinx.android.synthetic.main.toolbar.view.*

class HireProjectActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    View.OnClickListener {
    private lateinit var binding: ActivityHireProjectBinding
    private lateinit var viewModel: ProjectViewModel
    private lateinit var viewModel2: HireViewModel
    private lateinit var list: List<ProjectModel>
    private var dataFreelancers: DetailFreelancersModel? = null
    private var data = ArrayList<String>()
    private var idProject = "0"

    companion object {
        const val DATA_FREELANCERS = "data_freelancers"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hire_project)

        dataFreelancers = intent.getParcelableExtra(DetailFreelancersActivity.DATA_FREELANCERS)

        viewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        val service =
            ApiClient.getApiClient(this)?.create(ProjectApiService::class.java)
        if (service != null) {
            viewModel.setProjectService(service)
        }
        viewModel.getSharedPreference(this)
        subscribeLiveData()

        viewModel2 = ViewModelProvider(this).get(HireViewModel::class.java)
        val service2 =
            ApiClient.getApiClient(this)?.create(HireApiService::class.java)
        if (service2 != null) {
            viewModel2.setHireService(service2)
        }

        binding.inc.tv_nameToolbar.text = "Hire Freelancer"
        binding.spinnerProject.onItemSelectedListener = this
        binding.btnSend.setOnClickListener(this)
    }

    private fun subscribeLiveData() {
        viewModel.idAccountLiveData.observe(this, Observer {
            viewModel.getProjectApi(it)
        })

        viewModel.listLiveData.observe(this, Observer {
            list = it
            for (i in it.indices) {
                data.add(it[i].name)
            }
            val dataAdapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, data
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            binding.spinnerProject.adapter = dataAdapter
        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        idProject = list[position].id
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send -> {
                viewModel2.createHireApi(
                    idProject,
                    dataFreelancers?.id_freelancers,
                    binding.edtMessage.text.toString(),
                    binding.edtJob.text.toString(),
                    binding.edtPrice.text.toString()
                )
                viewModel2.messageLiveData.observe(this, Observer {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
                finish()
            }
        }
    }
}