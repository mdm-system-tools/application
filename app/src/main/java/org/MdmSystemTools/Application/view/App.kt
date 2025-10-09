package org.MdmSystemTools.Application.view

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination


@Composable
fun App(
	currentDestination: NavDestination?,
	navigateToTopLevelDestination: (BottomBarItem) -> Unit,
	content: @Composable () -> Unit
) {
	NavigationSuiteScaffold(
		navigationSuiteItems = {
			bottomBarItems.forEach { item ->
				item(
					icon = {
						Icon(
							item.icon,
							contentDescription = stringResource(item.label)
						)
					},
					label = { Text(stringResource(item.label)) },
					selected = currentDestination?.route == item.route,
					onClick = { navigateToTopLevelDestination(item) }
				)
			}
		}
	) {
		content
	}
	//Scaffold(
	//	topBar = TopBarFactory.make(
	//		navDestination
	//	),
	//	bottomBar = BottomFac.make(
	//		navDestination,
	//		currentItem,
	//		appState.navHostController
	//	),
	//	floatingActionButton = FloatingButtonFactory.make(
	//		navDestination,
	//		appState::navigateToEventForm
	//	)
	//) { innerPadding -> Route(appState = appState, modifier = Modifier.padding(innerPadding)) }
}