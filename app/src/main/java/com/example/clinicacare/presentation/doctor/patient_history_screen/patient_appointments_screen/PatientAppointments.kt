package com.example.clinicacare.presentation.doctor.patient_history_screen.patient_appointments_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.presentation.doctor.components.PatientAppointmentItem
import com.example.clinicacare.presentation.doctor.patient_history_screen.PatientHistoryEvent
import com.example.clinicacare.presentation.doctor.patient_history_screen.PatientHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientAppointments(
    itemIndex: String?,
    patientHistoryViewModel: PatientHistoryViewModel = hiltViewModel()
) {

    val historyState by patientHistoryViewModel.historyState.collectAsState()
    val appointments = historyState.appointments
    val patientName = historyState.patientName

    val lazyListState = rememberLazyListState()

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
                        text = "Consultas de $patientName",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { values ->

        LaunchedEffect(key1 = itemIndex) {
            if (itemIndex != "-1") {
                patientHistoryViewModel.onEvent(PatientHistoryEvent.OnShowPatientAppointments(itemIndex))
            }
        }

        appointments.ifEmpty {
            Column(
                modifier = Modifier
                    .padding(values)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.ErrorOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(100.dp).padding(bottom = 17.dp)
                )
                Text(
                    text = "Este paciente não possui nenhuma consulta!",
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
        ) {
            items(appointments) { appointment ->
                PatientAppointmentItem(
                    appointment = appointment
                )
            }
        }
    }

}