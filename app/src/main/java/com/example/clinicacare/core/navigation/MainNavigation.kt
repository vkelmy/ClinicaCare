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
import com.example.clinicacare.presentation.patient.Patient
import com.example.clinicacare.presentation.doctor.Professional

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

        composable(route = Screen.PatientScreen.route) {
            Patient(navController = navController)
        }

        composable(route = Screen.ProfessionalScreen.route) {
            Professional(navController = navController)
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

        composable(route = Screen.ResetPassword.route) {
            ResetPassword(navController = navController)
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
    }
}