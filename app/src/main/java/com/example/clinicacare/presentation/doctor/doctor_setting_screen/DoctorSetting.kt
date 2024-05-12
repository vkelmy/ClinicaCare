package com.example.clinicacare.presentation.doctor.doctor_setting_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.doctor.components.DoctorSettingIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorSetting(
    navController: NavController,
    doctorSettingViewModel: DoctorSettingViewModel = hiltViewModel()
) {

    val user by doctorSettingViewModel.user.collectAsState()

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
                        text = "Configurações",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 7.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.PersonPin,
                    contentDescription = "person icon",
                    Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = user.name,
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            DoctorSettingIcon(
                "Mudar senha",
                onClick = { navController.navigate(Screen.ResetPassword.route) },
                trailingIcon = Icons.Filled.Lock,
                isResetPass = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            DoctorSettingIcon(
                "Nos avalie",
                trailingIcon = Icons.Filled.StarRate
            )
            Spacer(modifier = Modifier.height(8.dp))
            DoctorSettingIcon(
                "Versão",
                "1.0.0"
            )
            Spacer(modifier = Modifier.height(8.dp))
            DoctorSettingIcon(
                "Política de privacidade",
                trailingIcon = Icons.Filled.ChevronRight
            )
            Spacer(modifier = Modifier.height(8.dp))
            DoctorSettingIcon(
                "Sobre nós",
                trailingIcon = Icons.Filled.ChevronRight
            )
            Spacer(modifier = Modifier.height(8.dp))
            DoctorSettingIcon(
                "Sair",
                trailingIcon = Icons.AutoMirrored.Filled.Logout,
                isLogout = true,
                onClick = {
                    doctorSettingViewModel.logout()
                    navController.navigate(Screen.LoginScreen.route)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}