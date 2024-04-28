package com.example.clinicacare.core.presentation.event

sealed class ResetPassEvent {
    data class OnOldPassChange(val oldPass: String): ResetPassEvent()
    data class OnNewPassChange(val newPass: String): ResetPassEvent()
}