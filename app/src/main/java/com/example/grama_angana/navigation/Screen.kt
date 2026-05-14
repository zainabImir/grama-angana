package com.example.grama_angana.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Home)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
    object Applications : Screen("applications", "Applications")

    object Booking : Screen("booking", "Booking")
    object Login : Screen("login", "Login")
    object Register : Screen("register", "Register")
    
    companion object {
        val bottomNavItems = listOf(Dashboard, Profile, Settings)
    }
}