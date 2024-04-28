package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.event

sealed class AdminEvent {
    data class OnFilterAdmins(val query: String): AdminEvent()
}