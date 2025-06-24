package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    }

    val firstName = sharedPreferences.getString("first_name", "") ?: ""
    val lastName = sharedPreferences.getString("last_name", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Header
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(80.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Profile information:",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "First name: $firstName",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Last name: $lastName",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Email: $email",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        // Log out button pinned to the bottom
        Button(
            onClick = {
                sharedPreferences.edit().clear().apply()
                navController.navigate("onboarding") {
                    popUpTo(0) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Log out", fontWeight = FontWeight.Bold)
        }
    }
}
