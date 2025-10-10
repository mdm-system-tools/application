package org.MdmSystemTools.Application.view

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute


@Composable
fun App(
	currentDestination: NavDestination?,
	navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
	content: @Composable () -> Unit
) {
	NavigationSuiteScaffold(
		navigationSuiteItems = {
			TOP_LEVEL_DESTINATIONS.forEach { item: TopLevelDestination ->
				item(
					icon = {
						Icon(
							painterResource(id = item.selectedIcon),
							contentDescription = stringResource(item.iconTextId)
						)
					},
					label = { Text(stringResource(item.iconTextId)) },
					selected = currentDestination?.hasRoute(item::class) ?: false,
					onClick = { navigateToTopLevelDestination(item) }
				)
			}
		}
	) {
		content()
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