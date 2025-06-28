package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.example.littlelemon.ui.theme.KarlaFont
import com.example.littlelemon.ui.theme.MarkaziFont


@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    //var menuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    var searchPhrase by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    // State to hold the selected category
    var selectedCategory by remember { mutableStateOf("") }
    var orderMenuItems by remember { mutableStateOf(false) }

    // 🚀 Local menuItems variable with sorting
    var menuItems = if (orderMenuItems) {
        databaseMenuItems.sortedBy { it.title }
    } else {
        databaseMenuItems
    }

    // 🔍 Apply search filter
    if (searchPhrase.isNotEmpty()) {
        menuItems = menuItems.filter {
            it.title.contains(searchPhrase, ignoreCase = true) ||
                    it.description.contains(searchPhrase, ignoreCase = true) ||
                    it.category.contains(searchPhrase, ignoreCase = true)
        }
    }

    // Get unique categories from menu items
    val categories = menuItems.map { it.category }.distinct()

    // 🗂️ Apply category filter (optional for future)
    if (selectedCategory.isNotEmpty()) {
        menuItems = menuItems.filter {
            it.category == selectedCategory
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // 🔝 Header (Logo + Profile)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .width(150.dp)
                            .height(100.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                navController.navigate(Profile.route)
                            }
                    )
                }
            }

            // ⭐ Hero Section - Whole block inside green background
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF495E57))
                        .padding(16.dp)
                ) {
                    // 🔝 Title Centered at the Top
                    Text(
                        text = "Little Lemon",
                        fontFamily = MarkaziFont,
                        fontSize = 60.sp,
                        color = Color(0xFFF4CE14),
                    )


                    // 🔽 Row for left text block + right image
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 🟨 Text Column (Chicago + description)
                        Column(
                            modifier = Modifier.weight(0.6f)
                        ) {
                            Text(
                                text = "Chicago",
                                fontFamily = MarkaziFont,
                                fontSize = 40.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                                fontFamily = KarlaFont,
                                fontSize = 20.sp,
                                color = Color.White,
                                lineHeight = 20.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // 🟦 Image on the right
                        Image(
                            painter = painterResource(id = R.drawable.hero_image),
                            contentDescription = "Hero Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .weight(0.4f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                    TextField(
                        value = searchPhrase,
                        onValueChange = { searchPhrase = it },
                        placeholder = { Text("Enter Search Phrase") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            }


            // 📄 Category filter
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ORDER FOR DELIVERY!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Horizontal category buttons
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                    ) {
                        categories.forEach { category ->
                            Button(
                                onClick = {
                                    selectedCategory = if (selectedCategory == category) "" else category
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedCategory == category)
                                        Color(0xFFF4CE14) else Color(0xFFEDEFEE)
                                ),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    text = category,
                                    color = Color(0xFF4F6461),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }


            if (searchPhrase.isNotEmpty()) {
                menuItems = menuItems.filter {
                    it.title.contains(searchPhrase, ignoreCase = true)
                }
            }

            // 🍽️ Menu Items
            items(menuItems) { menuItem ->
                MenuItemRow(menuItem)
            }
        }
    }
}
