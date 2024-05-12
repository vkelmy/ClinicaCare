package com.example.clinicacare.presentation.doctor.patient_history_screen

import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.User

data class PatientHistoryState (
    val searchQuery: String = "",
    var patients: List<User> = emptyList(),
    var appointments: List<Appointment> = emptyList(),
    var patientName: String = ""
)