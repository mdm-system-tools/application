package org.MdmSystemTools.Application.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

//TODO mudar ROUTES e BottomAppBarItem para sealed class
enum class ROUTES(val route:String){
	ASSOCIATE("associate"),
	COLLABORATION("collaboration"),
	CALENDAR("calendar"),
	LOGIN("login"),
	REGISTER("register"),
	FORM("form")
}

data class BottomAppBarItem(val label:String, val icon: ImageVector, val route:String)
val bottomAppBarItems = listOf(
	BottomAppBarItem(
		label = "Associados",
		icon = Icons.Default.Home,
		route =  ROUTES.ASSOCIATE.name
	),
	BottomAppBarItem(
		label = "Colaboradores",
		icon = Icons.Default.Groups,
		route = ROUTES.COLLABORATION.name
	),
	BottomAppBarItem(
		label = "Agenda",
		icon = Icons.Default.CalendarToday,
		route = ROUTES.CALENDAR.name
	)
)

@Composable
fun BottomApp (
	item: BottomAppBarItem,
	modifier: Modifier = Modifier,
	items: List<BottomAppBarItem> = emptyList(),
	onItemChange: (BottomAppBarItem) -> Unit = {}
) {
	NavigationBar {
		items.forEach {
			val label = it.label
			val icon = it.icon
			NavigationBarItem(
				icon = { Icon(icon, contentDescription = label) },
				label = { Text(label) },
				selected = item.label == label,
				onClick = {
					onItemChange(it)
				}
			)
		}
	}
}

@Preview
@Composable
fun BottomAppBarPreview() {
	MaterialTheme {
		BottomApp(
			item = bottomAppBarItems.first(),
			items = bottomAppBarItems
		)
	}
}