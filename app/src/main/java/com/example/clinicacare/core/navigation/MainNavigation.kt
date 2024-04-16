package com.example.clinicacare.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.clinicacare.presentation.auth.login_screen.Login
import com.example.clinicacare.presentation.auth.signup_screen.Signup

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
    }
}