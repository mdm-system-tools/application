package org.MdmSystemTools.Application.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy


@Composable
fun AppNavigation(
	currentDestination: NavDestination?,
	navigateToTopLevelDestination: (Route) -> Unit,
	content: @Composable () -> Unit,
) {
	val navLayoutType = NavigationSuiteType.NavigationBar
	// Criar a estrutura da tela e adiciona uma barra de navegação
	NavigationSuiteScaffoldLayout(
		layoutType = navLayoutType,
		navigationSuite = {
			when (navLayoutType) {
				NavigationSuiteType.NavigationBar -> BottomNavigationBar(
					currentDestination,
					navigateToTopLevelDestination
				)
			}
		}
	) {
		// essa variavel representa a tela atual do navigation
		content()
	}
}

@Composable
fun BottomNavigationBar(
	currentDestination: NavDestination?,
	navigateToTopLevelDestination: (Route) -> Unit
) {
	NavigationBar {
		TOP_LEVEL_DESTINATIONS.forEach { (item, destination) ->
			NavigationBarItem(
				selected = currentSelected(currentDestination, destination),
				onClick = { navigateToTopLevelDestination(destination) },
				icon = {
					Icon(
						painterResource(item.icon),
						contentDescription = stringResource(item.label)
					)
				},
				label = { Text(stringResource(item.label)) },
			)
		}
	}
}

private fun currentSelected(currentDestination: NavDestination?, destination: Route): Boolean {
	return currentDestination?.hierarchy?.any {
		it.hasRoute(destination::class)
	} == true
}