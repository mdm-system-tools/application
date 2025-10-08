package org.MdmSystemTools.Application.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

sealed interface BottomBarItem {
	val label: String
	val icon: ImageVector
	val route: String

	data object Associate : BottomBarItem {
		override val label = "Associados"
		override val icon = Icons.Default.Home
		override val route = Screen.Associate.route
	}

	data object Collaboration : BottomBarItem {
		override val label = "Colaboradores"
		override val icon = Icons.Default.Groups
		override val route = Screen.Collaboration.route
	}

	data object Calendar : BottomBarItem {
		override val label = "Agenda"
		override val icon = Icons.Default.CalendarToday
		override val route = Screen.Calendar.route
	}
}

val bottomBarItems = listOf(
	BottomBarItem.Associate, BottomBarItem.Collaboration, BottomBarItem.Calendar
)

@Composable
fun BottomApp(
	itemSelected: BottomBarItem,
	navController: NavController,
) {
	NavigationBar {
		bottomBarItems.forEach { item ->
			val label = item.label
			val icon = item.icon
			NavigationBarItem(
				icon = { Icon(icon, contentDescription = label) },
				label = { Text(label) },
				selected = item.label == itemSelected.label,
				onClick = {
					if (item.route != itemSelected.route) {
						navController.navigate(item.route) {
							launchSingleTop = true
							popUpTo(item.route)
						}
					}
				}
			)
		}
	}
}