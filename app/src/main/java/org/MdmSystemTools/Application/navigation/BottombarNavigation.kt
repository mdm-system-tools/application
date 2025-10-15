package org.MdmSystemTools.Application.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.ScreenLockPortrait
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.view.screens.Calendar.CalendarScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen

fun NavGraphBuilder.associate() {
	composable<Route.Associate> {
		AssociateListScreen(modifier = Modifier)
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

@Composable
fun createBottomBar() {

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

data class TopLevelDestination(
	val route: Route,
	val icon: ImageVector,
	val label: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
	TopLevelDestination(
		route = Route.Associate,
		icon = Icons.Default.ScreenLockPortrait,
		label = R.string.label_associate,
	),
	TopLevelDestination(
		route = Route.Collaboration,
		icon = Icons.Default.Replay,
		label = R.string.label_collaboration,
	),
	TopLevelDestination(
		route = Route.Calendar,
		icon = Icons.Default.Science,
		label = R.string.label_calendar,
	)
)

