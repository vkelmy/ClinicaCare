package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.add_admin_screen.event

sealed class AddAdminEvent {
    data class OnNameChange(val name: String): AddAdminEvent()
    data class OnEmailChange(val email: String): AddAdminEvent()
    data class OnBirthdateChange(val birthdate: String): AddAdminEvent()
    data class OnSexChange(val sex: String): AddAdminEvent()
    data class OnNumberChange(val number: String): AddAdminEvent()
    data class OnCpfChange(val cpf: String): AddAdminEvent()
}