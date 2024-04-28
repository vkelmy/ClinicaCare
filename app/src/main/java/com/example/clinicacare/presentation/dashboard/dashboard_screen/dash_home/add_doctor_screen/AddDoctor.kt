package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.add_doctor_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.auth.util.InputCpfFormatted
import com.example.clinicacare.presentation.auth.util.InputDateFormatted
import com.example.clinicacare.presentation.auth.util.InputNumberFormatted
import com.example.clinicacare.presentation.auth.util.MasksDefaults
import com.example.clinicacare.presentation.dashboard.dashboard_screen.components.AddUserInputTextField
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.add_doctor_screen.event.AddDoctorEvent
import kotlinx.coroutines.launch

@Composable
fun AddDoctor(
    navController: NavController,
    addDoctorViewModel: AddDoctorViewModel = hiltViewModel()
) {
    val addDoctorState by addDoctorViewModel.addDoctorState.collectAsState()

    val name = addDoctorState.name
    val email = addDoctorState.email
    val birthdate = addDoctorState.birthdate
    val sex = addDoctorState.sex
    val number = addDoctorState.number
    val profession = addDoctorState.profession
    val crm = addDoctorState.crm
    val cpf = addDoctorState.cpf
    val isEmailTaken = addDoctorState.isEmailTaken

    val scope = rememberCoroutineScope()

    val isFieldsNotEmpty =
        name.isNotEmpty() && email.isNotEmpty()
        && birthdate.isNotEmpty() && sex.isNotEmpty() && number.isNotEmpty()
        && cpf.isNotEmpty() && crm.isNotEmpty() && profession.isNotEmpty()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Adicionar Doutor",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(14.dp)
            )
            AddUserInputTextField(
                value = name,
                onValueChange = {
                    addDoctorViewModel.onEvent(AddDoctorEvent.OnNameChange(it))
                },
                labelText = "Nome",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth()
            )
            AddUserInputTextField(
                value = birthdate,
                onValueChange = {
                    if (it.length < MasksDefaults.BIRTHDATE_LENGTH)
                        addDoctorViewModel.onEvent(AddDoctorEvent.OnBirthdateChange(it))
                },
                labelText = "Aniversário",
                leadingIcon = Icons.Default.Cake,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
                visualTransformation = InputDateFormatted()
            )
            AddUserInputTextField(
                value = sex,
                onValueChange = {
                    addDoctorViewModel.onEvent(AddDoctorEvent.OnSexChange(it))
                },
                labelText = "Sexo",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth()
            )
            AddUserInputTextField(
                value = number,
                onValueChange = {
                    if (it.length < MasksDefaults.NUMBER_LENGTH)
                        addDoctorViewModel.onEvent(AddDoctorEvent.OnNumberChange(it))
                },
                labelText = "Número",
                leadingIcon = Icons.Default.Phone,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
                visualTransformation = InputNumberFormatted()
            )
            AddUserInputTextField(
                value = profession,
                onValueChange = {
                    addDoctorViewModel.onEvent(AddDoctorEvent.OnProfessionChange(it))
                },
                labelText = "Profissão",
                leadingIcon = Icons.Default.Work,
                modifier = Modifier.fillMaxWidth()
            )
            AddUserInputTextField(
                value = crm,
                onValueChange = {
                    addDoctorViewModel.onEvent(AddDoctorEvent.OnCrmChange(it))
                },
                labelText = "CRM",
                leadingIcon = Icons.Default.Badge,
                modifier = Modifier.fillMaxWidth()
            )
            AddUserInputTextField(
                value = cpf,
                onValueChange = {
                    if (it.length < MasksDefaults.CPF_LENGTH)
                        addDoctorViewModel.onEvent(AddDoctorEvent.OnCpfChange(it))
                },
                labelText = "CPF",
                leadingIcon = Icons.Default.PermIdentity,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
                visualTransformation = InputCpfFormatted()
            )
            AddUserInputTextField(
                value = email,
                onValueChange = {
                    addDoctorViewModel.onEvent(AddDoctorEvent.OnEmailChange(it))
                },
                labelText = "Email",
                leadingIcon = Icons.Default.Email,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email,
                isError = isEmailTaken,
                supportingText = {
                    if (isEmailTaken) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Esse email já existe!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            Button(
                onClick = {
                    scope.launch {
                        val isEmailAlreadyTaken = addDoctorViewModel.addDoctor()
                        if (!isEmailAlreadyTaken) navController.navigate(Screen.DashboardScreen.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                    disabledContentColor = MaterialTheme.colorScheme.primary
                ),
                enabled = isFieldsNotEmpty
            ) {
                Text(
                    text = "Cadastrar",
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}