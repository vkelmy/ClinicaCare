package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.event

import org.mongodb.kbson.ObjectId

sealed class DashboardEvent {
    data class OnDeleteProfessional(val id: ObjectId): DashboardEvent()
    data class OnFilterDoctors(val query: String): DashboardEvent()
    data class OnShowDoctorInfo(val id: String?): DashboardEvent()
}