package org.MdmSystemTools.Application

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.MdmSystemTools.Application.view.screens.Registration.ProfilesListScreen
import org.MdmSystemTools.Application.view.screens.Registration.FormScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Meeting.CalendarNavigation

enum class Destination(
	val route: String,
	val label: String,
	val icon: ImageVector,
	val contentDescription: String
) {
	ASSOCIATES("associates", "Associados", Icons.Default.Home, "Associados"),
	COLLABORATORS("collaborators", "Colaboradores", Icons.Default.Groups, "Colaboradores"),
	CALENDAR("calendar", "Agenda", Icons.Default.CalendarToday, "Agenda"),
}

@Composable
fun AppNavHost(
	navController: NavHostController,
	startDestination: Destination,
	modifier: Modifier = Modifier
) {
	NavHost(
		navController,
		startDestination = startDestination.route
	) {
		Destination.entries.forEach { destination ->
			composable(destination.route) {
				when (destination) {
					Destination.ASSOCIATES -> ProfilesListScreen(navController)
					Destination.COLLABORATORS -> CollaboratorsScreen()
					Destination.CALENDAR -> CalendarNavigation()
				}
			}
		}
		// TODO REFATORING Melhorar a forma de lidar com mutiplas telas
		composable("Formulario") {
			FormScreen()
		}
	}
}
