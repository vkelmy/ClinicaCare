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
    data object DoctorInfoScreen: Screen("doctorInfo")
    data object AdminInfoScreen: Screen("adminInfo")
    data object PatientInfoScreen: Screen("patientInfo")

    data object PatientScreen: Screen("patient")

    data object ResetPassword: Screen("resetPassword")

    data object ProfessionalScreen: Screen("professional")
    data object PatientHistoryScreen: Screen("patientHistory")
    data object PatientAppointmentsScreen: Screen("patientAppointments")
    data object PatientAppointmentInfoScreen: Screen("patientAppointmentInfo")
    data object DoctorSetting: Screen("doctorSetting")
    data object AppointmentInfoScreen: Screen("appointmentInfo")
    data object AppointmentReturnScreen: Screen("appointmentReturn")
}