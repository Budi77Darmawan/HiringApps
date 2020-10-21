package com.example.hiringapps.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hiringapps.LogoutDialog
import com.example.hiringapps.R
import com.example.hiringapps.api.ApiClient
import com.example.hiringapps.api.profile.ProfileApiService
import com.example.hiringapps.databinding.FragmentProfile2Binding
import com.google.android.material.navigation.NavigationView

class Profile2Fragment : Fragment(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
    private lateinit var binding: FragmentProfile2Binding
    private lateinit var mDrawer: DrawerLayout
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var viewModel: ProfileViewModel
    private lateinit var data: ProfileModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (shouldInterceptBackPress()) {
                    mDrawer.closeDrawer(GravityCompat.END)
                } else {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    private fun shouldInterceptBackPress(): Boolean {
        return mDrawer.isDrawerOpen(GravityCompat.START)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val service =
            ApiClient.getApiClient(requireContext())?.create(ProfileApiService::class.java)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.getSharedPreference(requireContext())
        if (service != null) {
            viewModel.setProfileService(service)
        }
        subscribeLiveData()

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile2, container, false
        )

        val activity = activity as AppCompatActivity

        binding.navView.setNavigationItemSelectedListener(this)
        binding.btnEdit.setOnClickListener(this)

        mDrawer = binding.drawerLayout
        mDrawerToggle = ActionBarDrawerToggle(
            activity,
            mDrawer,
            R.string.open,
            R.string.close
        )
        mDrawer.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        binding.imgMenu.setOnClickListener(this)

        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.faq -> {
                Toast.makeText(requireContext(), "FREQUENTLY ASKED QUESTIONS", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.help -> {
                Toast.makeText(requireContext(), "HELPING !!!!!!", Toast.LENGTH_LONG).show()
            }
            R.id.about -> {
                Toast.makeText(requireContext(), "ABOUT ?????", Toast.LENGTH_LONG).show()
            }
            R.id.logout -> {
                LogoutDialog()
                    .show(
                        requireActivity().supportFragmentManager,
                        LogoutDialog.TAG
                    )
            }
        }
        mDrawer.closeDrawer(GravityCompat.END)
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_menu -> {
                if (!mDrawer.isDrawerOpen(GravityCompat.END)) {
                    mDrawer.openDrawer(GravityCompat.END)
                } else {
                    mDrawer.closeDrawer(GravityCompat.END)
                }
            }
            R.id.btn_edit -> {
                val intent = Intent(activity, EditProfileActivity::class.java)
                intent.putExtra(EditProfileActivity.EXTRA_DATA, data)
                startActivityForResult(intent, EditProfileActivity.UPDATE_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == EditProfileActivity.UPDATE_REQUEST_CODE) {
            subscribeLiveData()
        }
    }

    private fun subscribeLiveData() {
        viewModel.idAccountLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.getProfileApi(it)
        })

        viewModel.dataProfileLiveData.observe(viewLifecycleOwner, Observer {
            data = it
            val post = "${it.position} • ${it.company}"
            binding.apply {
                tvName.text = it.name
                tvNameCompany.text = post
                tvCity.text = it.city
                tvDescCompany.text = it.description
                Glide.with(requireActivity())
                    .load("http://10.0.2.2:8080/images/${it.image}")
                    .into(imgProfil)
            }
        })
    }
}


