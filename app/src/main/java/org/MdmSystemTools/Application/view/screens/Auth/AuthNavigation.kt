package org.MdmSystemTools.Application.view.screens.Auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class AuthDestination(val route: String) {
    object Login : AuthDestination("login")
    object Register : AuthDestination("register")
}

@Composable
fun AuthNavigation(
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AuthDestination.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AuthDestination.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(AuthDestination.Register.route)
                },
                onLoginSuccess = onAuthSuccess
            )
        }

        composable(AuthDestination.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = onAuthSuccess
            )
        }
    }
}