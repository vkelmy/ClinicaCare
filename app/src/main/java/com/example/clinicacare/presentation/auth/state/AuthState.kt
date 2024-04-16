package com.example.clinicacare.presentation.auth.state

data class AuthState (
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val birthdate: String = "",
    val sex: String = "",
    val number: String = "",
    val cpf: String = "",
    val isChecked: Boolean = false,
    var routePath: String = "",
    var userRole: String = "",
    var userId: String = "",
    var isUserLogged: Boolean = false
)