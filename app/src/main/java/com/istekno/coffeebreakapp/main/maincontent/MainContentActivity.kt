package com.istekno.coffeebreakapp.main.maincontent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityMainContentBinding

class MainContentActivity : BaseActivity<ActivityMainContentBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main_content
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbMenuMaincontent)
        drawer = binding.drawerLayout

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToggle = ActionBarDrawerToggle(this, drawer, binding.tbMenuMaincontent,
            R.string.open,
            R.string.close
        )

        binding.navView.setNavigationItemSelectedListener(this)
        initialHomePage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
        } else super.onBackPressed()
    }

    private fun fragmentProperties(mFragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_maincontent, mFragment)
            commit()
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val toolbar = binding.tbMenuMaincontent
        val title = binding.toolbarTitle

        when (item.itemId) {
            R.id.nav_home -> {
                fragmentProperties(HomeFragment(toolbar, title))
            }

            R.id.nav_profile -> {
                fragmentProperties(ProfileFragment(toolbar, title))
            }

            R.id.nav_order -> {
                fragmentProperties(OrderFragment(toolbar, title))
            }

            R.id.nav_setting -> {
                fragmentProperties(SettingFragment(toolbar, title))
            }
        }
        return false
    }

    private fun initialHomePage() {
        val toolbar = binding.tbMenuMaincontent
        val title = binding.toolbarTitle

        fragmentProperties(HomeFragment(toolbar, title))
    }
}