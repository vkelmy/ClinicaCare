package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.add_doctor_screen.state

data class AddDoctorState (
    val filter: String = "",
    val email: String = "",
    val name: String = "",
    val birthdate: String = "",
    val sex: String = "",
    val number: String = "",
    val profession: String = "",
    val crm: String = "",
    val cpf: String = "",
    var isEmailTaken: Boolean = false
)