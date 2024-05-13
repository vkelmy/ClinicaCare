package com.example.clinicacare.presentation.patient.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.presentation.patient.PatientViewModel
import com.example.clinicacare.presentation.patient.event.PatientEvent

@Composable
    fun DoctorDropdownMenu(
    items: List<User>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    label: String,
    viewModel: PatientViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(selectedItem) }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .clickable { expanded = true },
            enabled = false,
            label = { Text(label, fontSize = 17.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                disabledBorderColor = MaterialTheme.colorScheme.onPrimary,
                disabledLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 10.dp)
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            val id = item._id.toString().split("BsonObjectId(", ")")[1]
                            viewModel.onEvent(PatientEvent.ChangeDoctor(id))
                            onItemSelected(item.name)
                            text = item.name
                            expanded = false
                        },
                        text = {
                            Text(item.name)
                        }
                    )
                }
            }
        }
    }
}
