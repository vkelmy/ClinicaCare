package com.example.clinicacare.core.navigation

sealed class Screen(val route: String) {
    data object LoginScreen: Screen("login")
    data object SignupScreen: Screen("signup")
    data object DashboardScreen: Screen("dashboard")
    data object PatientScreen: Screen("patient")
    data object ProfessionalScreen: Screen("professional")
    data object PatientHistoryScreen: Screen("patientHistory")
    data object ProfessionalInfoScreen: Screen("professionalInfo")
}