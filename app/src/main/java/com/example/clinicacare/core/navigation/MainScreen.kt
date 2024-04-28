package com.example.clinicacare.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.clinicacare.presentation.dashboard.bottom_nav_bar.BottomNavBar
import com.example.clinicacare.presentation.dashboard.bottom_nav_bar.provideBottomNavItems

@Composable
fun MainScreen(startDestination: String) {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val rootDestinations = listOf(
        Screen.DashboardScreen.route,
        Screen.DashPatientScreen.route,
        Screen.DashAdminScreen.route,
        Screen.DashSettingsScreen.route
    )

    val bottomNavBarState = remember { mutableStateOf(true) }
    val navBarEntry by navController.currentBackStackEntryAsState()
    bottomNavBarState.value = rootDestinations.contains(navBarEntry?.destination?.route)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = bottomNavBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavBar(
                    items = provideBottomNavItems(),
                    navController
                ) {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { values ->
        Box(
            modifier = Modifier.padding(values)
        ) {
            MainNavigation(
                navController = navController,
                startDestination = startDestination
            )
        }
    }
}