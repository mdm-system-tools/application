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

fun NavController.navigateToDashboard() {
	navigate(route = Route.AssociateList)
}

fun NavGraphBuilder.register() {
	composable<Register> {
		// TODO esse registro esta quebrado
		SignUpScreen(onNavigateToLogin = {
			//		appState::navigateToLogin
		}, onRegisterSuccess = {
			//	appState::navigateToLogin
		})
	}
}
