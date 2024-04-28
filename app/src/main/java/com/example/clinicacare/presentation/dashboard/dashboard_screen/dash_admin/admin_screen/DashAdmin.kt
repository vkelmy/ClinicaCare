package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.admin_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.dashboard.dashboard_screen.components.UsersList
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.event.AdminEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashAdmin(
    navController: NavController,
    dashAdminViewModel: DashAdminViewModel = hiltViewModel()
) {

    val adminState by dashAdminViewModel.adminState.collectAsState()
    val searchQuery = adminState.searchQuery
    val users = adminState.admins

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
                        text = "Administradores",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.AddAdminDash.route) },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add new admins",
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
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
        ) {
            UsersList(
                query = searchQuery,
                onValueChange = {
                    dashAdminViewModel.onEvent(AdminEvent.OnFilterAdmins(it))
                },
                users = users,
                navController = navController,
                route = Screen.AdminInfoScreen.route
            )
        }
    }
}