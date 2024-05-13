package com.example.clinicacare.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.clinicacare.core.presentation.ResetPassword
import com.example.clinicacare.presentation.auth.login_screen.Login
import com.example.clinicacare.presentation.auth.signup_screen.Signup
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.components.DoctorInfo
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.add_admin_screen.AddAdmin
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.admin_screen.DashAdmin
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.components.AdminInfo
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.Dashboard
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.add_doctor_screen.AddDoctor
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.DashPatient
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.components.PatientInfo
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_settings.DashSettings
import com.example.clinicacare.presentation.doctor.Doctor
import com.example.clinicacare.presentation.doctor.components.AppointmentInfo
import com.example.clinicacare.presentation.doctor.doctor_setting_screen.DoctorSetting
import com.example.clinicacare.presentation.doctor.patient_history_screen.PatientHistory
import com.example.clinicacare.presentation.doctor.patient_history_screen.patient_appointments_screen.PatientAppointments
import com.example.clinicacare.presentation.doctor.return_screen.AppointmentReturn
import com.example.clinicacare.presentation.patient.Patient
import com.example.clinicacare.presentation.patient.PatientAddAppointment
import com.example.clinicacare.presentation.patient.patient_setting_screen.PatientSetting

@Composable
fun MainNavigation(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.LoginScreen.route) {
            Login(navController = navController)
        }
        composable(route = Screen.SignupScreen.route) {
            Signup(navController = navController)
        }


        composable(route = Screen.DashboardScreen.route) {
            Dashboard(navController = navController)
        }
        composable(route = Screen.AddDoctorDash.route) {
            AddDoctor(navController = navController)
        }
        composable(route = Screen.DashPatientScreen.route) {
            DashPatient(navController = navController)
        }
        composable(route = Screen.DashAdminScreen.route) {
            DashAdmin(navController = navController)
        }
        composable(route = Screen.AddAdminDash.route) {
            AddAdmin(navController = navController)
        }
        composable(route = Screen.DashSettingsScreen.route) {
            DashSettings(navController = navController)
        }
        composable(route = "${Screen.AdminInfoScreen.route}/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) { index ->
            AdminInfo(
                itemIndex = index.arguments?.getString("index")
            )
        }
        composable(route = "${Screen.DoctorInfoScreen.route}/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) { index ->
            DoctorInfo(
                itemIndex = index.arguments?.getString("index")
            )
        }


        composable(route = Screen.ProfessionalScreen.route) {
            Doctor(navController = navController)
        }
        composable(route = "${Screen.PatientInfoScreen.route}/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) { index ->
            PatientInfo(
                itemIndex = index.arguments?.getString("index")
            )
        }
        composable(route = "${Screen.AppointmentInfoScreen.route}/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { index ->
            AppointmentInfo(
                navController = navController,
                itemIndex = index.arguments?.getInt("index")
            )
        }
        composable(route = "${Screen.AppointmentReturnScreen.route}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) { id ->
            AppointmentReturn(
                navController = navController,
                appointmentId = id.arguments?.getString("id")
            )
        }
        composable(route = Screen.PatientHistoryScreen.route) {
            PatientHistory(navController = navController)
        }
        composable(route = "${Screen.PatientAppointmentsScreen.route}/{itemIndex}",
            arguments = listOf(
                navArgument("itemIndex") {
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) { itemIndex ->
            PatientAppointments(
                itemIndex = itemIndex.arguments?.getString("itemIndex")
            )
        }
        composable(route = Screen.DoctorSetting.route) {
            DoctorSetting(navController = navController)
        }


        composable(route = Screen.PatientScreen.route) {
            Patient(navController = navController)
        }
        composable(route = Screen.PatientAddAppointmentScreen.route) {
            PatientAddAppointment(navController = navController)
        }
        composable(route = Screen.PatientSetting.route) {
            PatientSetting(navController = navController)
        }

        composable(route = Screen.ResetPassword.route) {
            ResetPassword(navController = navController)
        }
    }
}