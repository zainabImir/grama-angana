package com.example.grama_angana.navigation

import com.example.grama_angana.ui.screens.maintainance.MaintenanceJarScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.grama_angana.ui.screens.applications.ApplicationsScreen
import com.example.grama_angana.ui.screens.auth.LoginScreen
import com.example.grama_angana.ui.screens.dashboard.DashboardScreen
import com.example.grama_angana.ui.screens.profile.ProfileScreen
import com.example.grama_angana.ui.screens.settings.SettingsScreen
import com.example.grama_angana.ui.screens.booking.BookingScreen
import com.example.grama_angana.ui.screens.calendar.EventCalendarScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route)
                },
                onNavigateToRegister = {

                }
            )
        }

        composable(route = Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(route = Screen.Applications.route) {
            ApplicationsScreen(navController = navController)
        }

        composable(route = "booking_form_route/{selectedDate}") { backStackEntry ->
            val dateArg = backStackEntry.arguments?.getString("selectedDate") ?: ""
            BookingScreen(
                navController = navController,
                preSelectedDate = dateArg
            )
        }

        composable(route = "calendar_route") {
            EventCalendarScreen(navController = navController)
        }

        composable(route = Screen.Applications.route) {
            MaintenanceJarScreen()
        }
    }
}