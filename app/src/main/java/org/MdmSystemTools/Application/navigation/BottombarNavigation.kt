package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.view.screens.Calendar.CalendarScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen

fun NavGraphBuilder.associate() {
	composable<Route.Associate> {
		AssociateListScreen()
	}
}

fun NavGraphBuilder.collaboration() {
	composable<Route.Collaboration> {
		CollaboratorsScreen()
	}
}

fun NavGraphBuilder.calendar() {
	composable<Route.Calendar> {
		CalendarScreen(
			onNavigateToAddEvent = {
				//appState::navigateToEventForm
			})
	}
}

@Serializable
sealed interface Route {
	@Serializable
	data object Associate : Route

	@Serializable
	data object Collaboration : Route

	@Serializable
	data object Calendar : Route

}

@Serializable
data class TopLevelDestination(
	val route: Route,
	val icon: Int,
	val label: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
	TopLevelDestination(
		route = Route.Associate,
		icon =  R.drawable.ic_associate,
		label = R.string.label_associate,
	),
	TopLevelDestination(
		route = Route.Collaboration,
		icon =  R.drawable.ic_associate,
		label = R.string.label_collaboration,
	),
	TopLevelDestination(
		route = Route.Calendar,
		icon = R.drawable.ic_associate,
		label = R.string.label_calendar,
	)
)

class NavigationActions(private val navController: NavHostController) {
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

