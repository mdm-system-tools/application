package org.MdmSystemTools.Application.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.datatransport.runtime.Destination

object BottomBarFactory {
	@Composable
	fun make(
		navDestination: NavDestination?,
		item: BottomBarItem,
		navController: NavController
	): @Composable (() -> Unit) {
		return when (navDestination?.route) {
			Screen.Associate.route -> {
				{ Create(item, navController) }
			}

			Screen.Collaboration.route -> {
				{ Create(item, navController) }
			}

			Screen.Calendar.route -> {
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