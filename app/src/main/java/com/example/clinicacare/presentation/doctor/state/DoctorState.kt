package com.example.clinicacare.presentation.doctor.state

import com.example.clinicacare.core.data.models.Appointment

data class DoctorState (
    val dailyAppointments: List<Appointment> = emptyList(),
    val monthlyAppointments: List<Appointment> = emptyList()
)