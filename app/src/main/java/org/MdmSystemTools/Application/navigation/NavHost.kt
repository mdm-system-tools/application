package org.MdmSystemTools.Application.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberNavHost(navHostController: NavHostController = rememberNavController()): NavHost {
	return remember(navHostController) {
		NavHost(navHostController)
	}
}

class NavHost(val navHostController: NavHostController) {

	@Composable
	fun CreateNavHost() {
		NavHost(navController = navHostController, startDestination = Login) {
			associate()
			collaboration()
			calendar()
			login(
				onNavigateToDashboard = { navHostController.navigateToDashboard() },
				onNavigateToRegister = {}
			)
			form()
			addEvent()
			register()
		}
	}

	fun navigateTo(item: TopLevelDestination) {
		navHostController.navigate(item.route) {
			popUpTo(navHostController.graph.findStartDestination().id) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}
	}

	//TODO coloca esse getcurrent na navegação do login, assim o problema de usar
	//@composable não afeta o codigo
	fun getCurrentDestination(): NavDestination? {
		val backStackEntry by navHostController.currentBackStackEntryAsState()
		val currentDestination = backStackEntry?.destination

		return currentDestination
	}

	fun checkCurrentDestination(item: TopLevelDestination): Boolean {
		val currenct = getCurrentDestination()
		return currenct?.hasRoute(item::class) ?: false
	}
}