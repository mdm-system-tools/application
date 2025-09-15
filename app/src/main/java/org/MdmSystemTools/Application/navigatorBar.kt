package org.MdmSystemTools.Application

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.mdmsystemtools.application.presentation.ui.screens.Cadastro.ProfilesListScreen
import org.mdmsystemtools.application.presentation.ui.screens.Cadastro.FormScreen
import org.mdmsystemtools.application.presentation.ui.screens.Camera.SettingsScreen
import org.mdmsystemtools.application.presentation.ui.screens.Reunião.ReuniaoNavigation

enum class Destination(
	val route: String,
	val label: String,
	val icon: ImageVector,
	val contentDescription: String
) {
	CADASTROS("Cadastros", "Cadastros", Icons.Default.People, "Cadastros"),
	REUNIAO("Reuniao", "Reuniao", Icons.Default.CalendarToday, "Reuniao"),
	CONFIGURACAO("Configuração", "Configuração", Icons.Default.Settings, "Configuração"),
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
					Destination.CADASTROS -> ProfilesListScreen(navController)
					Destination.REUNIAO -> ReuniaoNavigation()
					Destination.CONFIGURACAO -> SettingsScreen()
				}
			}
		}
		// TODO REFATORING Melhorar a forma de lidar com mutiplas telas
		composable("Formulario") {
			FormScreen()
		}
	}
}
