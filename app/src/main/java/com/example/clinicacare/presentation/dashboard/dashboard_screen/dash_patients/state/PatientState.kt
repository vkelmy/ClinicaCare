package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.state

import com.example.clinicacare.core.data.models.User

data class PatientState(
    val searchQuery: String = "",
    var patients: List<User> = emptyList(),
    var patient: User = User()
)