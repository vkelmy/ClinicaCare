package com.example.clinicacare.presentation.doctor.return_screen.event

sealed class ReturnEvent {
    data class OnDateChange(val date: String): ReturnEvent()
    data class OnTimeChange(val time: String): ReturnEvent()
    data class OnMotiveChange(val motive: String): ReturnEvent()
    data object SaveReturn: ReturnEvent()
}