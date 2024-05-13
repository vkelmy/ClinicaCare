package com.example.clinicacare.presentation.patient.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen

@Composable
fun PatientDropDownMenu(
    navController: NavController
) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 10.dp)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "show menu",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(30.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.background.copy(alpha = .7f))
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Marcar",
                        fontSize = 17.sp
                    )
                },
                onClick = {
                    navController.navigate(Screen.PatientAddAppointmentScreen.route)
                },
                leadingIcon = {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Add new appointment")
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Configuração",
                        fontSize = 17.sp
                    )
                },
                onClick = {
                    navController.navigate(Screen.PatientSetting.route)
                },
                leadingIcon = {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            )
        }
    }
}