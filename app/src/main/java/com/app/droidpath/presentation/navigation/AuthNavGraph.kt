package com.app.droidpath.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.droidpath.presentation.auth.login.LoginScreen
import com.app.droidpath.presentation.auth.signup.SignUpScreen

/**
 * Auth nested navigation graph.
 */

fun NavGraphBuilder.authNavGraph(navigationController: NavController) {
    navigation<Routes.AuthNavGraph>(
        startDestination = Routes.Login
    ) {
        composable<Routes.Login> {
            LoginScreen(
                onCreateAccountClick = { navigationController.navigate(Routes.Signup) },
                onSignInSuccess = {
                    navigationController.navigate(Routes.DashboardNavGraph) {
                        popUpTo<Routes.AuthNavGraph> { inclusive = true }
                    }
                }
            )
        }
        composable<Routes.Signup> {
            SignUpScreen(
                onSignInClick = { navigationController.popBackStack() },
                onCreateAccountClick = { _, _, _ ->
                    navigationController.navigate(Routes.DashboardNavGraph) {
                        popUpTo<Routes.AuthNavGraph> { inclusive = true }
                    }
                },
            )
        }
    }

}