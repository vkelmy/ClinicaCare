package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.state

import com.example.clinicacare.core.data.models.User

data class AdminState (
    val searchQuery: String = "",
    var admins: List<User> = emptyList(),
    var admin: User = User()
)