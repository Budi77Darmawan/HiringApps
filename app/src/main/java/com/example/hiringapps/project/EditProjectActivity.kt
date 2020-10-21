package com.example.hiringapps.project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.project.ProjectApiService
import com.example.hiringapps.databinding.ActivityEditProjectBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.toolbar_add.view.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class EditProjectActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditProjectBinding
    private lateinit var viewModel: EditProjectViewModel
    private var imgFile: File? = null
    private var idProject = ""

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val UPDATE_CODE = 9013
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_project)

        viewModel = ViewModelProvider(this).get(EditProjectViewModel::class.java)
        val service = ApiClient.getApiClient(this)?.create(ProjectApiService::class.java)
        if (service != null) {
            viewModel.setProjectService(service)
        }
        subscribeLiveData()

        val dataProject = intent.getParcelableExtra<ProjectModel>(EXTRA_DATA)

        idProject = dataProject?.id.toString()
        binding.let {
            it.edtName.setText(dataProject?.name, TextView.BufferType.EDITABLE)
            it.edtDeadline.setText(dataProject?.deadline, TextView.BufferType.EDITABLE)
            it.edtDesc.setText(dataProject?.description, TextView.BufferType.EDITABLE)
            Glide.with(this@EditProjectActivity)
                .load("http://10.0.2.2:8080/images/${dataProject?.image}")
                .into(it.imgProject)

            it.inc.tv_toolbar_add.text = "Edit Project"
            it.inc.tv_save.setOnClickListener(this)
            it.inc.img_back.setOnClickListener(this)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_save -> {
                viewModel.updateProjectApi(
                    idProject,
                    binding.edtName.text.toString(),
                    binding.edtDeadline.text.toString(),
                    binding.edtDesc.text.toString(),
                    imgFile
                )
                setResult(Activity.RESULT_OK)
                finish()
            }
            R.id.img_back -> {
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            object : DefaultCallback() {
                override fun onImagePicked(
                    imageFile: File,
                    source: EasyImage.ImageSource,
                    type: Int
                ) {
                    CropImage.activity(Uri.fromFile(imageFile))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setFixAspectRatio(true)
                        .start(this@EditProjectActivity)
                }
                override fun onImagePickerError(
                    e: Exception,
                    source: EasyImage.ImageSource,
                    type: Int
                ) {
                    super.onImagePickerError(e, source, type)
                    Toast.makeText(this@EditProjectActivity, "" + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val uri = result.uri
                Glide.with(applicationContext)
                    .load(File(uri.path))
                    .into(binding.imgProject)
                imgFile = File(uri.path)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val exception = result.error
                Toast.makeText(this, "" + exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun subscribeLiveData() {
        viewModel.messageLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

}