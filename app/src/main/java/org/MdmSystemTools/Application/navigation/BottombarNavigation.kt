package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.view.screens.Calendar.CalendarScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
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
	@Serializable
	data object AssociateForm: Route
}

@Serializable
data class TopLevelDestination(
	val icon: Int,
	val label: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
	TopLevelDestination(
		icon = R.drawable.ic_associate,
		label = R.string.label_associate,
	) to Route.Associate,
	TopLevelDestination(
		icon = R.drawable.ic_associate,
		label = R.string.label_collaboration,
	) to Route.Collaboration,
	TopLevelDestination(
		icon = R.drawable.ic_associate,
		label = R.string.label_calendar,
	) to Route.Calendar
)

class NavigationActions(private val navController: NavHostController) {
	fun navigateTo(destination: Route) {
		navController.navigate(destination) {
			popUpTo(navController.graph.findStartDestination().id) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}
	}
}