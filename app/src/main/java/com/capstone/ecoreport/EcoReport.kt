package com.capstone.ecoreport

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.capstone.ecoreport.data.auth.AuthManager
import com.capstone.ecoreport.navigation.NavigationItem
import com.capstone.ecoreport.navigation.Screen
import com.capstone.ecoreport.ui.screen.DetailScreen
import com.capstone.ecoreport.ui.screen.EditProfileScreen
import com.capstone.ecoreport.ui.screen.HomeScreen
import com.capstone.ecoreport.ui.screen.LoginScreen
import com.capstone.ecoreport.ui.screen.MapsScreen
import com.capstone.ecoreport.ui.screen.ProfileScreen
import com.capstone.ecoreport.ui.screen.RegisterScreen

@Composable
fun EcoReport(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { dummyId ->
                        navController.navigate(Screen.Detail.createRoute(dummyId))
                    }
                )
            }
            composable(Screen.Maps.route) {
                MapsScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("dummyId") { type = NavType.IntType }
                )
            ) {
                val id = it.arguments?.getInt("dummyId") ?: -1
                DetailScreen(
                    dummyId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.menu_home),
            icon = Icons.Outlined.Home,
            iconSelected = Icons.Filled.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(R.string.menu_maps),
            icon = Icons.Outlined.Map,
            iconSelected = Icons.Filled.Map,
            screen = Screen.Maps
        ),
        NavigationItem(
            title = stringResource(R.string.menu_profile),
            icon = Icons.Outlined.AccountCircle,
            iconSelected = Icons.Filled.AccountCircle,
            screen = Screen.Profile
        ),
    )

    NavigationBar(
        modifier = modifier
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.screen.route) item.iconSelected else item.icon,
                        contentDescription = item.title
                    )
                },
                selected = currentRoute == item.screen.route,
                label = {
                    Text(
                        item.title,
                        fontWeight = if (currentRoute == item.screen.route) FontWeight.Bold else FontWeight.Normal,
                    )
                },
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}