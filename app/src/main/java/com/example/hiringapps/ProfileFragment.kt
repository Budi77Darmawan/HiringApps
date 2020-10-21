package com.example.hiringapps

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.hiringapps.databinding.FragmentProfileBinding
import com.example.hiringapps.sharedpref.Constant
import com.example.hiringapps.sharedpref.SharedPrefProvider
import com.google.android.material.navigation.NavigationView

class ProfileFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private lateinit var sharedPref: SharedPrefProvider
    private lateinit var binding: FragmentProfileBinding

    private lateinit var mDrawer: DrawerLayout
    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedPref = SharedPrefProvider(requireContext())

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(shouldInterceptBackPress()){
                    mDrawer.closeDrawer(GravityCompat.END)
                }else{
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        sharedPref = SharedPrefProvider(requireActivity().applicationContext)
        binding.tvName.text = sharedPref.getString(Constant.KEY_NAME)

        val activity = activity as AppCompatActivity

        binding.navView.setNavigationItemSelectedListener(this)

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
        binding.webGithub.setOnClickListener(this)
        binding.webLinkedin.setOnClickListener(this)
        binding.addExp.setOnClickListener(this)

        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.faq -> {
                Toast.makeText(requireContext(), "FREQUENTLY ASKED QUESTIONS", Toast.LENGTH_LONG).show()
            }
            R.id.help -> {
                Toast.makeText(requireContext(), "HELPING !!!!!!", Toast.LENGTH_LONG).show()
            }
            R.id.about -> {
                Toast.makeText(requireContext(), "ABOUT ?????", Toast.LENGTH_LONG).show()
            }
            R.id.logout -> {
                LogoutDialog()
                    .show(requireActivity().supportFragmentManager, LogoutDialog.TAG)
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
            R.id.web_github -> {
                val intent = Intent (activity, WebViewActivity::class.java)
                intent.putExtra("url", "https://github.com/Budi77Darmawan?tab=repositories")
                requireActivity().startActivity(intent)
            }
            R.id.web_linkedin -> {
                val intent = Intent (activity, WebViewActivity::class.java)
                intent.putExtra("url", "https://www.linkedin.com/in/budi-darmawan-6141341a1")
                requireActivity().startActivity(intent)
            }
            R.id.add_exp -> {
                val intent = Intent (activity, AddExpActivity::class.java)
                requireActivity().startActivity(intent)
            }
        }

    }
}


