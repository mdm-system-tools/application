package org.MdmSystemTools.Application.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.map
import org.MdmSystemTools.Application.view.BottomBarItem
import org.MdmSystemTools.Application.view.bottomBarItems

@Composable
fun rememberAppState(navHostController: NavHostController = rememberNavController()): AppState {
	return remember(navHostController) {
		AppState(navHostController)
	}
}

class AppState(val navHostController: NavHostController) {
	val currenctRouteFlow = navHostController.currentBackStackEntryFlow.map {
		it.destination.route
	}

	fun navigateTo(item: BottomBarItem) {
		navHostController.navigate(item.route) {
			// Pop up to the start destination of the graph to
			// avoid building up a large stack of destinations
			// on the back stack as users select items
			//popUpTo(navHostController.graph.findStartDestination().id) {
			//	saveState = true
			//}
			// Avoid multiple copies of the same destination when
			// reselecting the same item
			//launchSingleTop = true
			// Restore state when reselecting a previously selected item
			//restoreState = true
		}
	}

	@Composable
	fun getCurrentDestination(): NavDestination? {
		val backStackEntry by navHostController.currentBackStackEntryAsState()
		val currentDestination = backStackEntry?.destination

		return currentDestination
	}

	@Composable
	fun getCurrentBottomBarItem(): BottomBarItem {
		val currentDestination = getCurrentDestination()
		val selectedItem by remember(currentDestination) {
			val item = currentDestination?.let { destination ->
				bottomBarItems.find {
					it.route == destination.route
				}
			} ?: bottomBarItems.first()
			mutableStateOf(item)
		}

		return selectedItem
	}

	private fun navigate(route: Route) {
		navHostController.navigate(route.destination)
	}

	fun navigateToAssociate() {
		navigate(Route.Associate)
	}

	fun navigateToCollaboration() {
		navigate(Route.Collaboration)
	}

	fun navigateToCalendar() {
		navigate(Route.Calendar)
	}

	fun navigateToLogin() {
		navigate(Route.Login)
	}

	fun navigateToRegister() {
		navigate(Route.Register)
	}

	fun navigateToAssociateForm() {
		navigate(Route.Form)
	}

	fun navigateToEventForm() {
		navigate(Route.AddEvent)
	}

	fun navigateBack() {
		navHostController.popBackStack()
	}
}