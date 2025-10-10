package org.MdmSystemTools.Application.view

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.R

@Serializable
sealed interface RouteObj {
	@Serializable
	data object Associate : RouteObj

	@Serializable
	data object Collaboration : RouteObj

	@Serializable
	data object Calendar : RouteObj
}

@Serializable
data class TopLevelDestination(
	val route: RouteObj,
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
		route = RouteObj.Associate,
		selectedIcon = R.drawable.ic_associate,
		unselectedIcon = R.drawable.ic_associate,
		iconTextId = R.string.label_associate,
	),
	TopLevelDestination(
		route = RouteObj.Collaboration,
		selectedIcon = R.drawable.ic_collaboration,
		unselectedIcon = R.drawable.ic_collaboration,
		iconTextId = R.string.label_collaboration,
	),
	TopLevelDestination(
		route = RouteObj.Calendar,
		selectedIcon = R.drawable.ic_calendar,
		unselectedIcon = R.drawable.ic_calendar,
		iconTextId = R.string.label_calendar,
	)
)