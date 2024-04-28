package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.add_doctor_screen.event

sealed class AddDoctorEvent {
    data class OnNameChange(val name: String): AddDoctorEvent()
    data class OnEmailChange(val email: String): AddDoctorEvent()
    data class OnBirthdateChange(val birthdate: String): AddDoctorEvent()
    data class OnSexChange(val sex: String): AddDoctorEvent()
    data class OnNumberChange(val number: String): AddDoctorEvent()
    data class OnProfessionChange(val profession: String): AddDoctorEvent()
    data class OnCrmChange(val crm: String): AddDoctorEvent()
    data class OnCpfChange(val cpf: String): AddDoctorEvent()
}