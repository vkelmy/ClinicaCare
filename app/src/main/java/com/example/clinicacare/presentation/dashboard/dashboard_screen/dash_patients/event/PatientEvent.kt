package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.event

import org.mongodb.kbson.ObjectId

sealed class PatientEvent {
    data class OnDeleteProfessional(val id: ObjectId): PatientEvent()
    data class OnFilterPatients(val query: String): PatientEvent()
    data class OnShowPatientInfo(val id: String?): PatientEvent()
}