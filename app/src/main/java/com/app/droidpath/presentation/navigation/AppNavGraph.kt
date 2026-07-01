package com.app.droidpath.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

/**
 *Root AppNavGraph — the entry point called from MainActivity.
 */
/*val startDestination = if (userIsLoggedIn) Routes.MainNavGraph
else Routes.AuthNavGraph*/

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.DashboardNavGraph) {
        authNavGraph(navController)
        dashboardNavGraph(navController)
    }
}