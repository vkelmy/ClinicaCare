package com.example.clinicacare.presentation.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.navigation.Screen
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Patient(
    patientViewModel: PatientViewModel = hiltViewModel(),
    navController: NavController
) {

    val patientState by patientViewModel.patientState.collectAsState()
    val userAppointments = patientState.userLoggedAppointments.asReversed()


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
                        text = "Agendamento",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 19.sp
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.PatientAddAppointmentScreen.route)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "Add new appointment",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(50.dp, 50.dp)
                                .background(MaterialTheme.colorScheme.secondary, CircleShape)
                                .padding(8.dp)
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(userAppointments) { appointment ->
                PatientAppointmentItems(appointment = appointment)
            }
        }
    }

}

@Composable
fun PatientAppointmentItems(
    appointment: Appointment
) {

    val localDate = LocalDateTime.now()
    val day = String.format(Locale.US, "%02d", localDate.dayOfMonth)
    val month = String.format(Locale.US, "%02d", localDate.monthValue)
    val year = localDate.year
    val hour = String.format(Locale.US, "%02d", localDate.hour)
    val minute = String.format(Locale.US, "%02d", localDate.minute)
    val date = "$day/$month/$year $hour:$minute"

    fun isOneDayLater(dateTimeStr1: String, dateTimeStr2: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val dateTime1 = LocalDateTime.parse(dateTimeStr1, formatter)
        val dateTime2 = LocalDateTime.parse(dateTimeStr2, formatter)

        val difference = ChronoUnit.DAYS.between(dateTime2.toLocalDate(), dateTime1.toLocalDate())

        return difference >= 1L
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 12.dp)
            .clickable {  }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 28.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "appointment date",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = appointment.date,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                )  {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "appointment time",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = appointment.time,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MedicalInformation,
                        contentDescription = "appointment doctor",
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = appointment.enrolledUsers[0].name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val formatDate = "${appointment.date} ${appointment.time}"
                    val checkDate = isOneDayLater(date, formatDate)
                    Icon(
                        imageVector = if (checkDate) {
                            Icons.Default.CheckCircle
                        } else {
                            Icons.Default.RadioButtonUnchecked
                        },
                        contentDescription = "appointment completed",
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = if (checkDate) {
                            "Concluído"
                        } else {
                            "Agendado"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.MedicalServices,
                    contentDescription = "appointment motive",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = appointment.motive,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}