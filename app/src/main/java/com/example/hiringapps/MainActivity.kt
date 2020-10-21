package com.example.hiringapps

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.hiringapps.databinding.ActivityMainBinding
import com.example.hiringapps.home.HomeFragment
import com.example.hiringapps.offers.OffersFragment
import com.example.hiringapps.profile.Profile2Fragment
import com.example.hiringapps.project.ProjectFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBack = false
    private var x = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        loadFragment(HomeFragment())

        binding.btmNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    if (x != 0) {
                        x = 0
//                        item.setIcon(R.drawable.ic_home)
                        loadFragment(HomeFragment())
                    }
                }
                R.id.offers -> {
                    if (x != 1) {
                        x = 1
                        loadFragment(OffersFragment())
                    }
                }
                R.id.projects -> {
                    if (x != 2) {
                        x = 2
                        loadFragment(ProjectFragment())
                    }
                }
                R.id.profile -> {
                    if (x != 3) {
                        x = 3
                        loadFragment(Profile2Fragment())
                    }
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onBackPressed() {
        if (doubleBack) {
            super.onBackPressed()
            return
        }

        this.doubleBack = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBack = false }, 3000)
    }
}
