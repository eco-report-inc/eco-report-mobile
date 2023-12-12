package com.capstone.ecoreport.ui.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RepeatOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.sp
import com.capstone.ecoreport.ui.theme.EcoReportTheme

enum class Screen {
    Home,
    Report,
    MapsScreen,
    ProfileScreen
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClicked: () -> Unit,
    onMapClicked: () -> Unit
) {

    var currentScreen by remember { mutableStateOf(Screen.Home) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Eco Report") },
                navigationIcon = {
                    IconButton(
                        onClick = { /* Handle navigation icon click */ }
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Handle search icon click */ }
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                    IconButton(
                        onClick = { /* Handle notifications icon click */ }
                    ) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ) {
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    selected = true,
                    onClick = { /* Handle home icon click */ },
                    label = { Text("Home") }
                )
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.RepeatOn, contentDescription = null) },
                    selected = false,
                    onClick = { /* Handle info icon click */ },
                    label = { Text("Report") }
                )
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Map, contentDescription = null) },
                    selected = currentScreen == Screen.MapsScreen,
                    onClick = {
                        onMapClicked()
                    },
                    label = { Text("Maps") }
                )

                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Person,
                        contentDescription = null) },
                    selected = currentScreen == Screen.ProfileScreen,
                    onClick = {
                        onProfileClicked()
                    },
                    label = { Text("Profile") }
                )
            }
        },
        content = { innerPadding ->
            // Your home screen content goes here
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Selamat Datang di Eco Report!", fontSize = 20.sp)
            }
        }
    )
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun HomeScreenPreview() {
    EcoReportTheme {
        HomeScreen(
            onProfileClicked = {},
            onMapClicked = {}
        )
    }
}
