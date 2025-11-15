package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Auth.SignInScreen
import org.MdmSystemTools.Application.view.screens.Auth.SignUpScreen

@Serializable
object Login

@Serializable
object Register

fun NavGraphBuilder.login(
	onNavigateToRegister: () -> Unit, onNavigateToDashboard: () -> Unit
) {
	composable<Login> {
		SignInScreen(onNavigateToRegister, onNavigateToDashboard)
	}
}

//fun NavController.navigateToDashboard() {
//	navigate(route = AssociateList)
//}

fun NavGraphBuilder.register(
	onNavigateToLogin: () -> Unit, onRegisterSuccess: () -> Unit
) {
	composable<Register> {
		SignUpScreen(onNavigateToLogin = onNavigateToLogin, onRegisterSuccess = onRegisterSuccess)
	}
}

fun NavController.navigateToLogin() {
	navigate(Login) {
		popUpTo(Login) { inclusive = true }
	}
}

fun NavController.navigateToRegister() {
	navigate(Register)
}