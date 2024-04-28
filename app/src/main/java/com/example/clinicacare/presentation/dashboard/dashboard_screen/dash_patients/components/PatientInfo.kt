package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.clinicacare.presentation.dashboard.dashboard_screen.components.AddUserInputTextField
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.DashboardViewModel
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.DashPatientViewModel
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.event.PatientEvent

@Composable
fun PatientInfo(
    itemIndex: String?,
    patientViewModel: DashPatientViewModel = hiltViewModel()
) {

    val dashState by patientViewModel.patientState.collectAsState()
    val user = dashState.patient

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
    ) { values ->

        LaunchedEffect(key1 = itemIndex) {
            if (itemIndex != "-1") {
                patientViewModel.onEvent(PatientEvent.OnShowPatientInfo(itemIndex))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = user.name,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(14.dp)
            )
            AddUserInputTextField(
                readOnly = true,
                value = user.name,
                onValueChange = {},
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth(),
                labelText = "Nome"
            )
            AddUserInputTextField(
                readOnly = true,
                value = user.email,
                onValueChange = {},
                leadingIcon = Icons.Default.Email,
                modifier = Modifier.fillMaxWidth(),
                labelText = "Email"
            )
            AddUserInputTextField(
                readOnly = true,
                value = user.birthdate,
                onValueChange = {},
                leadingIcon = Icons.Default.Cake,
                modifier = Modifier.fillMaxWidth(),
                labelText = "Aniversário"
            )
            AddUserInputTextField(
                readOnly = true,
                value = user.sex,
                onValueChange = {},
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth(),
                labelText = "Sexo"
            )
            AddUserInputTextField(
                readOnly = true,
                value = user.number,
                onValueChange = {},
                leadingIcon = Icons.Default.Phone,
                modifier = Modifier.fillMaxWidth(),
                labelText = "Número"
            )
            user.cpf?.let {
                AddUserInputTextField(
                    readOnly = true,
                    value = it,
                    onValueChange = {},
                    leadingIcon = Icons.Default.PermIdentity,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    labelText = "CPF"
                )
            }
        }
    }
}