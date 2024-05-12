package com.example.clinicacare.presentation.doctor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.InsertInvitation
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.doctor.DoctorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentInfo(
    itemIndex: Int?,
    doctorViewModel: DoctorViewModel = hiltViewModel(),
    navController: NavController
) {

    val doctorState by doctorViewModel.doctorState.collectAsState()
    val appointment = doctorState.appointment
    val patient = doctorState.patient

    val appointmentId = appointment._id.toString().split("BsonObjectId(", ")")[1]


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Consulta", fontSize = 27.sp, color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    IconButton(
                        onClick = { navController.navigate("${Screen.AppointmentReturnScreen.route}/$appointmentId") },
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(54.dp, 54.dp)
                                .background(MaterialTheme.colorScheme.secondary, CircleShape)
                                .padding(8.dp),
                            imageVector = Icons.Filled.InsertInvitation,
                            contentDescription = "marcar retorno",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { values ->

        LaunchedEffect(key1 = itemIndex) {
            if (itemIndex != -1) {
                doctorViewModel.displayAppointmentInfo(itemIndex)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppointmentTextField(
                readOnly = true,
                value = patient.name,
                onValueChange = {},
                labelText = "Nome",
                modifier = Modifier.padding(top = 30.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            AppointmentTextField(
                readOnly = true,
                value = appointment.date,
                onValueChange = {},
                labelText = "Data"
            )
            Spacer(modifier = Modifier.height(15.dp))
            AppointmentTextField(
                readOnly = true,
                value = appointment.time,
                onValueChange = {},
                labelText = "Horário"
            )
            Spacer(modifier = Modifier.height(15.dp))
            AppointmentTextField(
                readOnly = true,
                value = appointment.motive,
                onValueChange = {},
                labelText = "Observação"
            )
        }
    }
}