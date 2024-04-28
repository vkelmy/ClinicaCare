package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.add_admin_screen.state

import com.example.clinicacare.core.data.models.User

data class AddAdminState (
    val email: String = "",
    val name: String = "",
    val birthdate: String = "",
    val sex: String = "",
    val number: String = "",
    val profession: String = "",
    val cpf: String = "",
    var isEmailTaken: Boolean = false
)