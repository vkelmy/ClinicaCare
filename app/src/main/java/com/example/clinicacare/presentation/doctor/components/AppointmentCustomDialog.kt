package com.example.clinicacare.presentation.doctor.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.navigation.Screen

@Composable
fun AppointmentCustomDialog(
    onDismiss: () -> Unit,
    appointment: Appointment
) {

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
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                AppointmentTextField(
                    value = appointment.date,
                    onValueChange = {},
                    labelText = "Data",
                    modifier = Modifier
                        .padding(top = 30.dp),
                    readOnly = true,
                    enabled = false
                )

                Spacer(modifier = Modifier.height(15.dp))

                AppointmentTextField(
                    value = appointment.time,
                    onValueChange = {},
                    labelText = "Horário",
                    readOnly = true,
                    enabled = false
                )

                Spacer(modifier = Modifier.height(15.dp))

                AppointmentTextField(
                    value = appointment.motive,
                    onValueChange = {},
                    labelText = "Observação",
                    readOnly = true,
                    enabled = false
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                        disabledContentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = "voltar"
                    )
                }
            }
        }
    }
}