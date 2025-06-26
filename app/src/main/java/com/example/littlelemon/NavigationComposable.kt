package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigation(navController: NavHostController, database: AppDatabase) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    }

    val isRegistered = sharedPreferences.getString("first_name", "").isNullOrEmpty().not()

    val startDestination = if (isRegistered) Home.route else Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            Home(navController, database)
        }
        composable(Profile.route) {
            Profile(navController)
        }
    }
}


