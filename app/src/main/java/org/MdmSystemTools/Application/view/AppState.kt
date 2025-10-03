package org.MdmSystemTools.Application.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.map

class AppState(val navHostController: NavHostController) {
	val currenctRouteFlow = navHostController.currentBackStackEntryFlow.map {
		it.destination.route
	}

	private fun navigate(screen: Screen) {
		navHostController.navigate(screen.route)
	}

	fun navigateToAssociate() {
		navigate(Screen.Associate)
	}

	fun navigateToCollaboration() {
		navigate(Screen.Collaboration)
	}

	fun navigateToCalendar() {
		navigate(Screen.Calendar)
	}

	fun navigateToLogin() {
		navigate(Screen.Login)
	}

	fun navigateToRegister() {
		navigate(Screen.Register)
	}

	fun navigateToForm() {
		navigate(Screen.Form)
	}
}

@Composable
fun rememberAppState(navHostController: NavHostController = rememberNavController()) =
	remember(navHostController) {
		AppState(navHostController)
	}

sealed interface Screen {
	val route: String

	data object Associate : Screen {
		override val route = "/associate"
	}

	data object Collaboration : Screen {
		override val route = "/collaboration"
	}

	data object Calendar : Screen {
		override val route = "/calendar"
	}

	data object Form : Screen {
		override val route = "/form"
	}

	data object Login : Screen {
		override val route = "/login"
	}

	data object Register : Screen {
		override val route = "/register"
	}
}