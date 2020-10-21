package com.example.hiringapps.profile

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
import com.example.hiringapps.api.account.AccountApiService
import com.example.hiringapps.databinding.ActivityEditProfileBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.toolbar_add.view.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class EditProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel
    private var imgFile: File? = null

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val UPDATE_REQUEST_CODE = 9013
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_edit_profile
        )

        val dataProfile = intent.getParcelableExtra<ProfileModel>(EXTRA_DATA)

        binding.apply {
            edtName.setText(dataProfile?.name, TextView.BufferType.EDITABLE)
            edtCompany.setText(dataProfile?.company, TextView.BufferType.EDITABLE)
            edtPosition.setText(dataProfile?.position, TextView.BufferType.EDITABLE)
            edtCity.setText(dataProfile?.city, TextView.BufferType.EDITABLE)
            edtDescription.setText(dataProfile?.description, TextView.BufferType.EDITABLE)
            Glide.with(this@EditProfileActivity)
                .load("http://10.0.2.2:8080/images/${dataProfile?.image}")
                .into(imgProfil)
        }

        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        val service = ApiClient.getApiClient(this)?.create(AccountApiService::class.java)
        if (service != null) {
            viewModel.setAccountService(service)
        }
        subscribeLiveData()

        binding.inc.tv_save.setOnClickListener(this)
        binding.tvChangePict.setOnClickListener(this)
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
                        .start(this@EditProfileActivity)
                }

                override fun onImagePickerError(
                    e: Exception,
                    source: EasyImage.ImageSource,
                    type: Int
                ) {
                    super.onImagePickerError(e, source, type)
                    Toast.makeText(this@EditProfileActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val uri = result.uri
                Glide.with(applicationContext)
                    .load(File(uri.path))
                    .into(binding.imgProfil)
                imgFile = File(uri.path)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val exception = result.error
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_save -> {
                updateAccount()
                updateRecruiter()
                setResult(Activity.RESULT_OK)
                finish()
            }
            R.id.tv_changePict -> {
                EasyImage.openChooserWithGallery(this, "Choose Image", 3)
            }
        }
    }

    private fun updateAccount() {
        viewModel.updateAccountApi(binding.edtName.text.toString())
    }

    private fun updateRecruiter() {
        viewModel.updateRecruiterApi(
            binding.edtCompany.text.toString(),
            binding.edtPosition.text.toString(),
            binding.edtCity.text.toString(),
            binding.edtDescription.text.toString(),
            imgFile
        )
    }

    private fun subscribeLiveData() {
        viewModel.messageLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
