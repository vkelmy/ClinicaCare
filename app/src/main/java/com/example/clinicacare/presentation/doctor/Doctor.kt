package com.example.clinicacare.presentation.doctor

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun Doctor(
    doctorViewModel: DoctorViewModel = hiltViewModel(),
    navController: NavController
) {

    Text(text = "Professional Screen")
}