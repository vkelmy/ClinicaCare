package com.example.clinicacare.presentation.doctor.return_screen.state

import com.example.clinicacare.core.data.models.Appointment

data class ReturnState (
    val appointment: Appointment = Appointment(),
    val date: String = "",
    val time: String = "",
    val motive: String = "",
    val doctorId: String = "",
    val patientId: String = "",
    var dailyAppointmentsTime: MutableList<String> = mutableListOf()
)