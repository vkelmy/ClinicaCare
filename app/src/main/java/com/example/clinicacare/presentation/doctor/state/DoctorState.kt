package com.example.clinicacare.presentation.doctor.state

import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.User

data class DoctorState(
    var dailyAppointments: List<Appointment> = emptyList(),
    val monthlyAppointments: List<Appointment> = emptyList(),
    val appointment: Appointment = Appointment(),
    var patient: User = User(),
    var doctor: User = User(),
    var doctorId: String = ""
)