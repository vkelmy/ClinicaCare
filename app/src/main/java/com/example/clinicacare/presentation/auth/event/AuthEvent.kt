package com.example.clinicacare.presentation.auth.event

sealed class AuthEvent {
    data class OnEmailChange(val email: String): AuthEvent()
    data class OnPasswordChange(val password: String): AuthEvent()
    data class OnNameChange(val name: String): AuthEvent()
    data class OnBirthdateChange(val birthdate: String): AuthEvent()
    data class OnSexChange(val sex: String): AuthEvent()
    data class OnNumberChange(val number: String): AuthEvent()
    data class OnCpfChange(val cpf: String): AuthEvent()
    data class OnCheckRememberMe(val checked: Boolean): AuthEvent()
    object OnRegister: AuthEvent()
}