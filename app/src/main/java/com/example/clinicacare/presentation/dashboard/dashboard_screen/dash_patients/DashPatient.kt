package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.dashboard.dashboard_screen.components.UsersList
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.event.PatientEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashPatient(
    navController: NavController,
    dashPatientViewModel: DashPatientViewModel = hiltViewModel()
) {

    val patientState by dashPatientViewModel.patientState.collectAsState()
    val searchQuery = patientState.searchQuery
    val users = patientState.patients

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
                ),
                title = {
                    Text(
                        text = "Pacientes",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
        ) {
            UsersList(
                query = searchQuery,
                onValueChange = {
                    dashPatientViewModel.onEvent(PatientEvent.OnFilterPatients(it))
                },
                users = users,
                navController = navController,
                route = Screen.PatientInfoScreen.route
            )
        }
    }
}