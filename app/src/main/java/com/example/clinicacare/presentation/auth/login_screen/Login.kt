package com.example.clinicacare.presentation.auth.login_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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

@Composable
fun Login(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {

    val defaultPadding = 16.dp
    val itemSpacing = 8.dp

    val authState by authViewModel.authState.collectAsState()

    val email = authState.email
    val password = authState.password
    val checked = authState.isChecked

    val isFieldsEmpty = email.isNotEmpty() && password.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(
            text = "Entrar",
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )
        LoginTextField(
            value = email,
            onValueChange = {
                authViewModel.onEvent(AuthEvent.OnEmailChange(it))
            },
            labelText = "Email",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = password,
            onValueChange = {
                authViewModel.onEvent(AuthEvent.OnPasswordChange(it))
            },
            labelText = "Senha",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        authViewModel.onEvent(AuthEvent.OnCheckRememberMe(it))
                    }
                )
                Text("Lembre de mim")
            }
            TextButton(
                onClick = {}) {
                Text("Esqueçeu a senha?")
            }
        }
        Spacer(Modifier.height(itemSpacing))
        Button(
            onClick = {
                val routePath = authViewModel.login()
                if (routePath.isNotEmpty())
                    navController.navigate(routePath)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp),
            enabled = isFieldsEmpty
        ) {
            Text("Entrar")
        }

        Spacer(Modifier.height(itemSpacing))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Não tem uma conta?")
            Spacer(Modifier.height(itemSpacing))
            TextButton(onClick = {
                navController.navigate(Screen.SignupScreen.route)
            }) {
                Text("Cadastre-se")
            }
        }

    }
}