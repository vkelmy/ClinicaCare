package com.example.clinicacare.presentation.patient.event

sealed class PatientEvent {
    data class ChangeDoctorExpertise(val expertise: String): PatientEvent()
    data class ChangeDoctor(val doctor: String): PatientEvent()
    data class ChangeAppointmentMotive(val motive: String): PatientEvent()
    data class ChangeAppointmentDate(val date: String): PatientEvent()
    data class ChangeAppointmentTime(val time: String): PatientEvent()
    data object SaveAppointment: PatientEvent()
}