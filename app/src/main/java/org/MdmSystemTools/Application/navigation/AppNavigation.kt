package org.MdmSystemTools.Application.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute


@Composable
fun AppNavigation(
	currentDestination: NavDestination?,
	navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
	content: @Composable () -> Unit,
) {
	// Criar a tela com barra de navegação
	NavigationSuiteScaffold(
		navigationSuiteItems = {
			TOP_LEVEL_DESTINATIONS.forEach {
				item(
					icon = { Icon(painterResource(it.icon), contentDescription = stringResource(it.label)) },
					label = { Text(stringResource(it.label)) },
					selected = currentDestination?.hasRoute(it::class) ?: false,
					onClick = { navigateToTopLevelDestination(it) }
				)
			}
		}
	) {
		// essa variavel representa a tela atual do navigation
		content()
	}
}