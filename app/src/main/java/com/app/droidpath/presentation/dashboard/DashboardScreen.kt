package com.app.droidpath.presentation.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.droidpath.presentation.home.HomeScreen
import com.app.droidpath.presentation.learn.LearnScreen
import com.app.droidpath.presentation.navigation.DroidBottomNavBar
import com.app.droidpath.presentation.navigation.DroidTopAppBar
import com.app.droidpath.presentation.navigation.Routes
import com.app.droidpath.presentation.profile.ProfileScreen

/**
 *  Main Controller owns the Scaffold with TopAppBar
 *  and BottomNavBar. Every screen registered here automatically gets
 *  the bars; no per-screen conditional checks needed.
 */

@Composable
fun DashboardScreen(rootNavController: NavController) {

    val mainNavController = rememberNavController()
    val backStackEntry by mainNavController.currentBackStackEntryAsState()

    fun navigateToTopLevel(route: Routes) {
        mainNavController.navigate(route) {
            popUpTo(mainNavController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    Scaffold(
        topBar = { DroidTopAppBar(onProfileClick = { navigateToTopLevel(Routes.Profile) }) },
        bottomBar = {
            DroidBottomNavBar(
                backStackEntry = backStackEntry,
                onItemClick = { route -> navigateToTopLevel(route) }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = Routes.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.Home> {
                HomeScreen()
            }
            composable<Routes.Learn> { LearnScreen() }
            composable<Routes.Profile> {
                ProfileScreen()
            }
        }
    }

}