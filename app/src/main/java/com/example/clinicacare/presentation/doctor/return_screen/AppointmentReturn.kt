package com.example.clinicacare.presentation.doctor.return_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.doctor.components.AppointmentTextField
import com.example.clinicacare.presentation.doctor.components.TimeCustomDialog
import com.example.clinicacare.presentation.doctor.return_screen.event.ReturnEvent
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentReturn(
    returnViewModel: AppointmentReturnViewModel = hiltViewModel(),
    navController: NavController,
    appointmentId: String?
) {

    val returnState by returnViewModel.returnState.collectAsState()
    val date = returnState.date
    val time = returnState.time
    val motive = returnState.motive
    val dailyAppointments = returnState.dailyAppointmentsTime


    var isDialogShown by remember { mutableStateOf(false) }


    val datePickerState = rememberDatePickerState(selectableDates = UnablePastSelectableDates)
    var isDateShown by remember { mutableStateOf(false) }
    val pickedDate = datePickerState.selectedDateMillis

    val isFieldsEmpty = date.isNotEmpty() && time.isNotEmpty() && motive.isNotEmpty()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Retorno", fontSize = 27.sp, color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { values ->

        LaunchedEffect(key1 = appointmentId) {
            if (appointmentId != null && appointmentId != "-1") {
                returnViewModel.getAppointment(appointmentId)
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
                value = date,
                onValueChange = {
                    returnViewModel.onEvent(ReturnEvent.OnDateChange(it))
                },
                labelText = "Data",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .clickable { isDateShown = true },
                readOnly = true,
                enabled = false
            )

            if (isDateShown) {
                DatePickerDialog(
                    onDismissRequest = { isDateShown = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                pickedDate?.let {
                                    val dateFormatted = formatDate(it)
                                    returnViewModel.onEvent(ReturnEvent.OnDateChange(dateFormatted))
                                    isDateShown = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(3.dp),
                            shape = CircleShape
                        ) {
                            Text(text = "confirmar", fontSize = 18.sp)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { isDateShown = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(3.dp),
                            shape = CircleShape
                        ) {
                            Text(text = "cancelar", fontSize = 18.sp)
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            AppointmentTextField(
                value = time,
                onValueChange = {
                    returnViewModel.onEvent(ReturnEvent.OnTimeChange(it))
                },
                labelText = "Horário",
                modifier = Modifier
                    .clickable {
                        returnViewModel.getDailyAppointments()
                        isDialogShown = true
                    },
                readOnly = true,
                enabled = false
            )

            Spacer(modifier = Modifier.height(15.dp))

            AppointmentTextField(
                value = motive,
                onValueChange = {
                    returnViewModel.onEvent(ReturnEvent.OnMotiveChange(it))
                },
                labelText = "Observação"
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                onClick = {
                    returnViewModel.onEvent(ReturnEvent.SaveReturn)
                    navController.navigate(Screen.ProfessionalScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                    disabledContentColor = MaterialTheme.colorScheme.primary
                ),
                enabled = isFieldsEmpty
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = "marcar"
                )
            }
        }

        if (isDialogShown) {
            TimeCustomDialog(
                onDismiss = { isDialogShown = false },
                onConfirm = { isDialogShown = false },
                appointments = dailyAppointments,
                viewModel = returnViewModel,
                date = date.isEmpty()
            )
        }
    }
}

private fun formatDate(dateLong: Long): String {
    val localDate = Instant.ofEpochMilli(dateLong).atZone(ZoneId.of("UTC")).toLocalDate()
    val day = localDate.dayOfMonth
    val month = localDate.monthValue
    val formattedDay = String.format("%02d", day)
    val formattedMonth = String.format("%02d", month)
    return "$formattedDay/$formattedMonth/${localDate.year}"
}

@OptIn(ExperimentalMaterial3Api::class)
object UnablePastSelectableDates: SelectableDates {
    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}