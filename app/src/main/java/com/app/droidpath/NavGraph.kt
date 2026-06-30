package com.app.droidpath

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.droidpath.auth.login.LoginScreen
import com.app.droidpath.auth.signup.SignUpScreen

object Routes {
    const val LOGIN = "Login"
    const val SIGNUP = "Signup"
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(onSignInSuccess = {
            }, onCreateAccountClick = {
                navController.navigate(Routes.SIGNUP)
            })
        }

        composable(Routes.SIGNUP) {
            SignUpScreen(onSignInClick = {
                navController.popBackStack()
            })
        }
    }
}