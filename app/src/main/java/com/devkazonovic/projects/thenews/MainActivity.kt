package com.devkazonovic.projects.thenews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.devkazonovic.projects.thenews.common.extensions.hide
import com.devkazonovic.projects.thenews.common.extensions.show
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navBar: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navBar = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navBar.setupWithNavController(navController)
        navigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            val pagesWithoutNavBar = arrayOf(R.id.languageSettingPage)
            if (pagesWithoutNavBar.contains(destination.id)) {
                navBar.hide()
            } else {
                navBar.show()
            }
        }
    }
}