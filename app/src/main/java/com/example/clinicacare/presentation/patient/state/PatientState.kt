package com.example.clinicacare.presentation.patient.state

import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.User

data class PatientState (
    val doctors: List<User> = emptyList(),
    val appointments: MutableList<String> = mutableListOf(),
    val userLoggedAppointments: MutableList<Appointment> = mutableListOf(),
    val date: String = "",
    val time: String = "",
    val motive: String = "",
    val doctor: String = "",
    var expertise: String = "",
    val userLoggedId: String = "",
)