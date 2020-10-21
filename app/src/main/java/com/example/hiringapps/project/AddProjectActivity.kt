package com.example.hiringapps.project

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.project.ProjectApiService
import com.example.hiringapps.databinding.ActivityAddProjectBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.toolbar_add.view.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource
import java.io.File


class AddProjectActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddProjectBinding
    private lateinit var viewModel: AddProjectViewModel
    private var imgFile: File? = null

    companion object {
        const val ADD_REQUEST_CODE = 9013
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_add_project
        )

        viewModel = ViewModelProvider(this).get(AddProjectViewModel::class.java)
        val service = ApiClient.getApiClient(this)?.create(ProjectApiService::class.java)
        if (service != null) {
            viewModel.setProjectService(service)
        }
        subscribeLiveData()

        binding.let {
            it.inc.tv_save.setOnClickListener(this)
            it.inc.img_back.setOnClickListener(this)
            it.btnChooseImage.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_save -> {
                val name = binding.edtName.text.toString()
                val deadline = binding.edtDeadline.text.toString()
                val description = binding.edtDescription.text.toString()
                val check = check(name, deadline, description)

                if (check) {
                    viewModel.addProjectApi(name, deadline, description, imgFile!!)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
            R.id.btn_chooseImage -> {
                permission()
                EasyImage.openChooserWithGallery(this, "Choose Image", 3)
            }
            R.id.img_back -> {
                finish()
            }
        }
    }

    private fun permission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            }
        }
    }

    private fun check(name: String?, deadline: String?, description: String?): Boolean {
        if (name.isNullOrEmpty()) {
            binding.edtName.error = "Enter Project Name"
            binding.edtName.requestFocus()
            return false
        }
        if (deadline.isNullOrEmpty()) {
            binding.edtDeadline.error = "Enter Deadline Project"
            binding.edtDeadline.requestFocus()
            return false
        }
        if (description.isNullOrEmpty()) {
            binding.edtDescription.error = "Enter Description"
            binding.edtDescription.requestFocus()
            return false
        }
        if (imgFile == null) {
            Toast.makeText(this, "Please Choose Image", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            object : DefaultCallback() {
                override fun onImagePicked(imageFile: File, source: ImageSource, type: Int) {
                    CropImage.activity(Uri.fromFile(imageFile))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setFixAspectRatio(true)
                        .start(this@AddProjectActivity)
                }

                override fun onImagePickerError(e: Exception, source: ImageSource, type: Int) {
                    super.onImagePickerError(e, source, type)
                    Toast.makeText(this@AddProjectActivity, "" + e.message, Toast.LENGTH_SHORT)
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
