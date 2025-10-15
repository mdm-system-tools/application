package org.MdmSystemTools.Application.view

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.MdmSystemTools.Application.navigation.TOP_LEVEL_DESTINATIONS
import org.MdmSystemTools.Application.navigation.TopLevelDestination


@Composable
fun App(
	checkSelected: (TopLevelDestination) -> Boolean,
	navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
	content: @Composable () -> Unit
) {
	NavigationSuiteScaffold(
		navigationSuiteItems = {
			TOP_LEVEL_DESTINATIONS.forEach {
				item(
					icon = { Icon(it.icon, contentDescription = stringResource(it.label)) },
					label = { Text(stringResource(it.label)) },
					selected = checkSelected(it),
					onClick = { navigateToTopLevelDestination(it) }
				)
			}
		}
	) {
		content()
	}
}