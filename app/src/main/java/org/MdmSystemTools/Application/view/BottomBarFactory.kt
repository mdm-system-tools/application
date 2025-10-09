package org.MdmSystemTools.Application.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import org.MdmSystemTools.Application.navigation.Route

object BottomBarFactory {
	@Composable
	fun make(
		navDestination: NavDestination?,
		item: BottomBarItem,
		navController: NavController
	): @Composable (() -> Unit) {
		return when (navDestination?.route) {
			Route.Associate.destination -> {
				{ Create(item, navController) }
			}

			Route.Collaboration.destination -> {
				{ Create(item, navController) }
			}

			Route.Calendar.destination -> {
				{ Create(item, navController) }
			}

			else -> {
				{ Delete() }
			}
		}
	}

	@Composable
	private fun Create(item: BottomBarItem, navController: NavController) {
		return BottomApp(
			itemSelected = item,
			navController = navController
		)
	}

	@Composable
	private fun Delete() {
		return Unit
	}
}