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
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.navigation.Route

sealed interface BottomBarItem {
	val label: Int
	val icon: ImageVector
	val route: String

	data object Associate : BottomBarItem {
		override val label = R.string.label_associate
		override val icon = Icons.Default.Home
		override val route = Route.Associate.destination
	}

	data object Collaboration : BottomBarItem {
		override val label = R.string.label_collaboration
		override val icon = Icons.Default.Groups
		override val route = Route.Collaboration.destination
	}

	data object Calendar : BottomBarItem {
		override val label = R.string.label_calendar
		override val icon = Icons.Default.CalendarToday
		override val route = Route.Calendar.destination
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
		//bottomBarItems.forEach { item ->
		//	val label = item.label
		//	val icon = item.icon
		//	NavigationBarItem(
		//		icon = { Icon(icon, contentDescription = label) },
		//		label = { Text(label) },
		//		selected = item.label == itemSelected.label,
		//		onClick = {
		//			if (item.route != itemSelected.route) {
		//				navController.navigate(item.route) {
		//					launchSingleTop = true
		//					popUpTo(item.route)
		//				}
		//			}
		//		}
		//	)
		//}
	}
}