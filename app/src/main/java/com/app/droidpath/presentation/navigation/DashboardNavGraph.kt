package com.app.droidpath.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.app.droidpath.presentation.dashboard.DashboardScreen

/**
 *  Main app nested navigation graph — owns the Scaffold with TopAppBar
 *  and BottomNavBar. Every screen registered here automatically gets
 *  the bars; no per-screen conditional checks needed.
 */


fun NavGraphBuilder.dashboardNavGraph(navController: NavHostController) {
    composable<Routes.DashboardNavGraph> {
        DashboardScreen(navController)
    }
}