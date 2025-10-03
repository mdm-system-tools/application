package org.MdmSystemTools.Application.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.map

class AppState(val navHostController: NavHostController) {
	val currenctRouteFlow = navHostController.currentBackStackEntryFlow.map {
		it.destination.route
	}

	private fun navigate(screen: Screen) {
		navHostController.navigate(screen.route)
	}

	@Composable
	fun getCurrentDestination(): NavDestination? {
		val backStackEntry by navHostController.currentBackStackEntryAsState()
		val currentDestination = backStackEntry?.destination

		return currentDestination
	}

	@Composable
	fun isShowAppBar(): Boolean {
		val currentDestination = getCurrentDestination()
		val isShowAppBar: Boolean = currentDestination?.let { destination ->
			bottomAppBarItems.any { it.route == destination.route }
		} ?: false

		return isShowAppBar
	}

	@Composable
	fun selectedItem(): BottomAppBarItem {
		val currentDestination = getCurrentDestination()
		val selectedItem by remember(currentDestination) {
			val item = currentDestination?.let { destination ->
				bottomAppBarItems.find {
					it.route == destination.route
				}
			} ?: bottomAppBarItems.first()
			mutableStateOf(item)
		}

		return selectedItem
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

	fun navigateToAddEvent(){
		navigate(Screen.AddEvent)
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
	data object AddEvent : Screen {
		override val route = "/addevent"
	}
}