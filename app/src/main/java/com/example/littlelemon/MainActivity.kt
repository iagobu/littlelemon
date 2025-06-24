package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonApp()
        }
    }
}

@Composable
fun LittleLemonApp() {
    LittleLemonTheme {
        val navController = rememberNavController()
        MyNavigation(navController = navController) // Call the function from NavigationComposable.kt
    }
}