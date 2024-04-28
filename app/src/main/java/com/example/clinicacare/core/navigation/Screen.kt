package com.example.clinicacare.core.navigation

sealed class Screen(val route: String) {
    data object LoginScreen: Screen("login")
    data object SignupScreen: Screen("signup")
    data object DashboardScreen: Screen("dashboard")
    data object DashPatientScreen: Screen("dashPatient")
    data object DashAdminScreen: Screen("dashAdmin")
    data object DashSettingsScreen: Screen("dashSettings")
    data object AddDoctorDash: Screen("addDoctor")
    data object AddAdminDash: Screen("addAdmin")
    data object PatientScreen: Screen("patient")
    data object ProfessionalScreen: Screen("professional")
    data object ResetPassword: Screen("resetPassword")
    data object PatientHistoryScreen: Screen("patientHistory")
    data object DoctorInfoScreen: Screen("doctorInfo")
    data object PatientInfoScreen: Screen("patientInfo")
    data object AdminInfoScreen: Screen("adminInfo")
}