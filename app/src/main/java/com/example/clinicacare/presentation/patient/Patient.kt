package com.example.clinicacare.presentation.patient

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen

@Composable
fun Patient(
    patientViewModel: PatientViewModel = hiltViewModel(),
    navController: NavController
) {
    Text(text = "Patient Screen")

}