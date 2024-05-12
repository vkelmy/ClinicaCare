package com.example.clinicacare.presentation.dashboard.bottom_nav_bar

import com.example.clinicacare.R
import com.example.clinicacare.core.navigation.Screen

data class NavBarItemHolder(
    val title: String,
    val icon: Int,
    val route: String
)
fun provideBottomNavItems() = listOf(
    NavBarItemHolder(
        "",
        R.drawable.doctor,
        Screen.DashboardScreen.route
    ),
    NavBarItemHolder(
        "",
        R.drawable.patient,
        Screen.DashPatientScreen.route
    ),
    NavBarItemHolder(
        "",
        R.drawable.admin,
        Screen.DashAdminScreen.route
    ),
    NavBarItemHolder(
        "",
        R.drawable.settings,
        Screen.DashSettingsScreen.route
    )
)