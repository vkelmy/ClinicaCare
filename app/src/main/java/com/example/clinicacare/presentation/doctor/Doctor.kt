package com.example.clinicacare.presentation.doctor


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.doctor.components.DailyAppointmentItem
import com.example.clinicacare.presentation.doctor.components.DropDownMenu
import com.example.clinicacare.presentation.doctor.components.MonthlyAppointment
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Doctor(
    doctorViewModel: DoctorViewModel = hiltViewModel(),
    navController: NavController
) {

    val doctorState by doctorViewModel.doctorState.collectAsState()
    val dailyAppointments = doctorState.dailyAppointments
    val monthlyAppointments = doctorState.monthlyAppointments

    val filterOptions = listOf("Dia", "Mês")
    var selectedOption by remember { mutableStateOf(filterOptions[0]) }
    var isExpanded by remember { mutableStateOf(false) }
    val icon = if (isExpanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp

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
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            contentPadding = PaddingValues(horizontal = 30.dp),
                            onClick = { isExpanded = !isExpanded },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                        ){
                            Text(
                                text = selectedOption,
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 19.sp
                            )
                            Icon(
                                imageVector = icon,
                                contentDescription = "more options button",
                            )
                        }
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false },
                        ) {
                            filterOptions.mapIndexed { index, label ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedOption = label
                                        doctorViewModel.getFilteredAppointments(index)
                                        isExpanded = false
                                    },
                                    text = {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = if (selectedOption == label)
                                                MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                                        )
                                    }
                                )
                            }
                        }

                    }
                },
                actions = {
                    DropDownMenu(navController = navController)
                }
            )
        }
    ) { padding ->

        when (selectedOption) {
            "Dia" -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    dailyAppointments.mapIndexed { index, appointment ->
                        DailyAppointmentItem(
                            dailyAppointment = appointment,
                            navController = navController,
                            itemIndex = index
                        )
                    }
                }

            }
            "Mês" -> {
                MonthlyAppointment(
                    monthlyAppointment = monthlyAppointments,
                    navController = navController,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}