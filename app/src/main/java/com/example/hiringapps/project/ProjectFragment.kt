package com.example.hiringapps.project

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.project.ProjectApiService
import com.example.hiringapps.databinding.FragmentProjectBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottomsheet_project.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ProjectFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProjectBinding
    private lateinit var viewModel: ProjectViewModel
    private lateinit var bottomsheetProject: BottomSheetDialog
    private lateinit var list: List<ProjectModel>
    private var idRv: Int = 0
    private var idProject: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)
        binding.rvApp.setHasFixedSize(true)

        viewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        val service =
            ApiClient.getApiClient(requireContext())?.create(ProjectApiService::class.java)
        if (service != null) {
            viewModel.setProjectService(service)
        }
        viewModel.getSharedPreference(requireContext())
        subscribeLiveData(0)

        binding.inc.tv_nameToolbar.text = "List Project"
        binding.inc.img_backToolbar.visibility = View.GONE

        bottomsheetProject = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottomsheet_project, null)
        bottomsheetProject.setContentView(view)

        binding.addProject.setOnClickListener(this)
        view.edit.setOnClickListener(this)
        view.delete.setOnClickListener(this)
        view.back.setOnClickListener(this)

        return binding.root
    }

    private fun showRecyclerList(list: List<ProjectModel>) {
        binding.rvApp.layoutManager = LinearLayoutManager(requireContext())
        val listProjectAdapter = ListProjectAdapter(list)
        binding.rvApp.adapter = listProjectAdapter

        listProjectAdapter.setOnItemClickCallback(object :
            ListProjectAdapter.OnItemClickCallback {

            override fun onIconClicked(id: Int) {
                idProject = list[id].id.toInt()
                idRv = id
                bottomsheetProject.show()
            }

            override fun onItemClicked(id: Int) {
                val intent = Intent(activity, DetailProjectActivity::class.java)
                intent.putExtra(DetailProjectActivity.EXTRA_DATA, list[id])
                startActivity(intent)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_project -> {
                val intent = Intent(activity, AddProjectActivity::class.java)
                startActivityForResult(intent, AddProjectActivity.ADD_REQUEST_CODE)
            }
            R.id.edit -> {
                bottomsheetProject.dismiss()
                val intent = Intent(activity, EditProjectActivity::class.java)
                intent.putExtra(EditProjectActivity.EXTRA_DATA, list[idRv])
                startActivityForResult(intent, EditProjectActivity.UPDATE_CODE)
            }
            R.id.delete -> {
                showDialog()
            }
            R.id.back -> {
                bottomsheetProject.dismiss()
            }
        }
    }

    private fun showDialog() {
        bottomsheetProject.dismiss()
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(
            requireContext()
        )

        alertDialogBuilder.setTitle("Delete Project")

        alertDialogBuilder
            .setMessage("Are you sure?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                subscribeLiveData(1)
                viewModel.deleteProjectApi(idProject)
                onActivityResult(9013, -1, null)
            }
            .setNegativeButton(
                "No"
            ) { dialog, _ ->
                dialog.cancel()
            }
        alertDialogBuilder.create()
        alertDialogBuilder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == AddProjectActivity.ADD_REQUEST_CODE) {
            subscribeLiveData(0)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == EditProjectActivity.UPDATE_CODE) {
            subscribeLiveData(0)
        }
        if (resultCode == -1 && requestCode == 9013) {
            subscribeLiveData(1)
        }
    }

    private fun subscribeLiveData(deleteCode: Int) {
        viewModel.idAccountLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.getProjectApi(it)
        })

        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            list = it
            showRecyclerList(it)
            if (it.isNotEmpty()) {
                binding.imgBox.visibility = View.GONE
                binding.descImg.visibility = View.GONE
            } else {
                binding.imgBox.visibility = View.VISIBLE
                binding.descImg.visibility = View.VISIBLE
            }
        })

        if (deleteCode == 1) {
            viewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }

    }
}
