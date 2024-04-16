package com.example.clinicacare.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(startDestination: String) {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
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