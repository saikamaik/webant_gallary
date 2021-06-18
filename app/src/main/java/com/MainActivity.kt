package com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.webant_gallery.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import presentation.ui.fragment.NewFragment
import presentation.ui.fragment.PopularFragment

class MainActivity : AppCompatActivity() {

    private val newFragment = NewFragment()
    private val popularFragment = PopularFragment()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_new -> openFragment(newFragment)
                R.id.navigation_popular -> openFragment(popularFragment)
            }
            true
        }

    private fun openFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            openFragment(newFragment)
        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}