package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.state

import com.example.clinicacare.core.data.models.User

data class DashboardState (
    val searchQuery: String = "",
    var users: List<User> = emptyList(),
    var user: User = User()
)