package com.example.clinicacare.presentation.patient.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.clinicacare.presentation.patient.PatientViewModel
import com.example.clinicacare.presentation.patient.event.PatientEvent

@Composable
fun PatientTimeCustomDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    appointments: MutableList<String>,
    viewModel: PatientViewModel,
    date: Boolean
) {
    val availableTime = if (date) {
        mutableListOf()
    } else {
        mutableListOf(
            "8:00", "8:30", "9:00",
            "9:30", "10:00", "10:30",
            "14:00", "14:30", "15:00",
            "15:30", "16:00", "16:30"
        )
    }

    availableTime.removeAll(appointments)

    var selectedItem by remember { mutableStateOf(viewModel.patientState.value.time) }

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(15.dp)
                )
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ){

                if (date) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Primeiro selecione uma data para saber os horários disponíveis!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.fillMaxWidth().padding(30.dp),
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = {
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = CircleShape
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = "voltar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(columns = GridCells.Fixed(4), horizontalArrangement = Arrangement.Center) {
                        items(availableTime) {
                            val isSelected = it == selectedItem
                            val background = if (isSelected) {
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
                            } else {
                                Color.Transparent
                            }

                            val textColor = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onPrimary
                            }

                            Card(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(6.dp)
                                    .clickable {
                                        selectedItem = it
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(background),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(3.dp),
                                        text = it,
                                        fontSize = 22.sp,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = {
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                            ,
                            shape = CircleShape
                        ) {
                            Text(
                                text = "cancelar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Button(
                            onClick = {
                                onConfirm()
                                viewModel.onEvent(PatientEvent.ChangeAppointmentTime(selectedItem))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "confirmar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

            }
        }
    }
}