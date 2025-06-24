package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Onboarding(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(100.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF495E57)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                color = Color.White,
                fontSize = 25.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Personal information",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 40.dp)
                )

                // First Name
                Text(text = "First name", fontSize = 14.sp, modifier = Modifier.align(Alignment.Start))
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Last Name
                Text(text = "Last name", fontSize = 14.sp, modifier = Modifier.align(Alignment.Start))
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Email
                Text(text = "Email", fontSize = 14.sp, modifier = Modifier.align(Alignment.Start))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Status message
                if (statusMessage.isNotBlank()) {
                    Text(
                        text = statusMessage,
                        color = if (statusMessage.contains("unsuccessful")) Color.Red else Color.Green,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                        statusMessage = "Registration unsuccessful. Please enter all data."
                    } else {
                        // Save data to SharedPreferences
                        val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
                        sharedPreferences.edit()
                            .putString("first_name", firstName)
                            .putString("last_name", lastName)
                            .putString("email", email)
                            .apply()

                        statusMessage = "Registration successful!"
                        navController.navigate(Home.route) {
                            popUpTo(0) { inclusive = true }
                        }
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
                Text(text = "Register", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    Onboarding(navController)
}
