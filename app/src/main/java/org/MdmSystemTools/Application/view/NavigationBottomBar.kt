package org.MdmSystemTools.Application.view

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.R

sealed interface Route {
	@Serializable
	data object Associate : Route

	@Serializable
	data object Collaboration : Route

	@Serializable
	data object Calendar : Route
}

data class TopLevelDestination(
	val route: Route,
	val selectedIcon: Int,
	val unselectedIcon: Int,
	val iconTextId: Int
)

class NavigationBottomBar(private val navController: NavController) {
	fun navigateTo(destination: TopLevelDestination) {
		navController.navigate(destination.route) {
			popUpTo(navController.graph.findStartDestination().id) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}
	}
}

val TOP_LEVEL_DESTINATIONS = listOf(
	TopLevelDestination(
		route = Route.Associate,
		selectedIcon = R.drawable.ic_associate,
		unselectedIcon = R.drawable.ic_associate,
		iconTextId = R.string.tab_associate,
	),
	TopLevelDestination(
		route = Route.Collaboration,
		selectedIcon = R.drawable.ic_collaboration,
		unselectedIcon = R.drawable.ic_collaboration,
		iconTextId = R.string.tab_collaboration,
	),
	TopLevelDestination(
		route = Route.Calendar,
		selectedIcon = R.drawable.ic_calendar,
		unselectedIcon = R.drawable.ic_calendar,
		iconTextId = R.string.tab_calendar,
	)
)