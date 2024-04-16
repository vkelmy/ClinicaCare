package com.example.clinicacare.presentation.auth.signup_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.auth.AuthViewModel
import com.example.clinicacare.presentation.auth.components.HeaderText
import com.example.clinicacare.presentation.auth.components.LoginTextField
import com.example.clinicacare.presentation.auth.event.AuthEvent
import com.example.clinicacare.presentation.auth.util.InputCpfFormatted
import com.example.clinicacare.presentation.auth.util.InputDateFormatted
import com.example.clinicacare.presentation.auth.util.InputNumberFormatted
import com.example.clinicacare.presentation.auth.util.MasksDefaults

@Composable
fun Signup(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {

    val defaultPadding = 16.dp
    val itemSpacing = 8.dp

    val authState by authViewModel.authState.collectAsState()

    val email = authState.email
    val password = authState.password
    val name = authState.name
    val birthdate = authState.birthdate
    val sex = authState.sex
    val number = authState.number
    val cpf = authState.cpf

    val isFieldsNotEmpty = name.isNotEmpty() && birthdate.isNotEmpty() &&
            email.isNotEmpty() && password.isNotEmpty() &&
            sex.isNotEmpty() && number.isNotEmpty() && cpf.isNotEmpty()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding)
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderText(
                text = "Cadastrar",
                modifier = Modifier
                    .padding(vertical = defaultPadding)
                    .align(alignment = Alignment.Start)
            )
            LoginTextField(
                value = name,
                onValueChange = {
                    authViewModel.onEvent(AuthEvent.OnNameChange(it))
                },
                labelText = "Nome",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = birthdate,
                onValueChange = {
                    if (it.length < MasksDefaults.BIRTHDATE_LENGTH)
                        authViewModel.onEvent(AuthEvent.OnBirthdateChange(it))
                },
                labelText = "Data de Nascimento",
                leadingIcon = Icons.Filled.Cake,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
                visualTransformation = InputDateFormatted()
            )
            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = sex,
                onValueChange = {
                    authViewModel.onEvent(AuthEvent.OnSexChange(it))
                },
                labelText = "Sexo",
                leadingIcon = Icons.Filled.Person,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = number,
                onValueChange = {
                    if (it.length < MasksDefaults.NUMBER_LENGTH)
                        authViewModel.onEvent(AuthEvent.OnNumberChange(it))
                },
                labelText = "Número",
                leadingIcon = Icons.Filled.Phone,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone,
                visualTransformation = InputNumberFormatted()
            )
            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = cpf,
                onValueChange = {
                    if (it.length < MasksDefaults.CPF_LENGTH)
                        authViewModel.onEvent(AuthEvent.OnCpfChange(it))
                },
                labelText = "CPF",
                leadingIcon = Icons.Filled.PermIdentity,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
                visualTransformation = InputCpfFormatted()
            )
            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = email,
                onValueChange = {
                    authViewModel.onEvent(AuthEvent.OnEmailChange(it))
                },
                labelText = "Email",
                leadingIcon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = password,
                onValueChange = {
                    authViewModel.onEvent(AuthEvent.OnPasswordChange(it))
                },
                labelText = "Senha",
                leadingIcon = Icons.Filled.Password,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(itemSpacing))
            Button(
                onClick = {
                    authViewModel.onEvent(AuthEvent.OnRegister)
                    navController.navigate(Screen.PatientScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp),
                enabled = isFieldsNotEmpty
            ) {
                Text("Cadastrar", modifier = Modifier.padding(4.dp))
            }

            Spacer(Modifier.height(itemSpacing))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Já tem uma conta?")
                Spacer(Modifier.height(itemSpacing))
                TextButton(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                ) {
                    Text("Entrar")
                }
            }

        }
    }
}