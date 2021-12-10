package com.devkazonovic.projects.thenews.common.extensions

import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.devkazonovic.projects.thenews.R


fun Toolbar.setUpNavigationGraph(navController: NavController) {
    val appBarConfiguration = AppBarConfiguration(navController.graph)
    this.setupWithNavController(navController, appBarConfiguration)
}



fun Toolbar.setMainPageToolbar(  navController: NavController,
                                 drawerLayout:DrawerLayout){
    val appBarConfiguration = AppBarConfiguration(setOf(
        R.id.forYouPage, R.id.headlinesPage, R.id.searchPage, R.id.followingPage
    ), drawerLayout)
    this.setupWithNavController(navController, appBarConfiguration)
    this.setOnMenuItemClickListener { menuItem ->
        when (menuItem.itemId) {
            R.id.page_lr_setting -> {
                navController.navigate(R.id.languageSettingPage)
                true
            }
            else -> false
        }
    }
    navController.addOnDestinationChangedListener { _, _, _ ->
        this.setNavigationIcon(R.drawable.ic_home_menu)
    }
}