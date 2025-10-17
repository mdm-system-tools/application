package org.MdmSystemTools.Application

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.MdmSystemTools.Application.navigation.AppNavigation
import org.MdmSystemTools.Application.navigation.NavigationActions
import org.MdmSystemTools.Application.navigation.Route
import org.MdmSystemTools.Application.navigation.addEvent
import org.MdmSystemTools.Application.navigation.associate
import org.MdmSystemTools.Application.navigation.calendar
import org.MdmSystemTools.Application.navigation.collaboration
import org.MdmSystemTools.Application.navigation.form
import org.MdmSystemTools.Application.navigation.login
import org.MdmSystemTools.Application.navigation.navigateToDashboard
import org.MdmSystemTools.Application.navigation.register


@Composable
fun App() {
	val navController = rememberNavController()
	val navigationActions = remember(navController) {
		NavigationActions(navController)
	}
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination

	Surface {
		AppNavigation(
			currentDestination = currentDestination,
			navigateToTopLevelDestination = navigationActions::navigateTo
		) {
			AppNavHost(navController)
		}
	}
}

@Composable
private fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
	NavHost(navController = navController, startDestination = Route.Associate) {
		calendar()
		associate()
		collaboration()
		login(
			onNavigateToDashboard = { navController.navigateToDashboard() },
			onNavigateToRegister = {}
		)
		form()
		addEvent()
		register()
	}
}