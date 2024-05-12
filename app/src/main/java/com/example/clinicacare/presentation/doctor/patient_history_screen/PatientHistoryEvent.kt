package com.example.clinicacare.presentation.doctor.patient_history_screen

sealed class PatientHistoryEvent {
    data class OnFilterPatients(val query: String): PatientHistoryEvent()
    data class OnShowPatientAppointments(val id: String?): PatientHistoryEvent()
}