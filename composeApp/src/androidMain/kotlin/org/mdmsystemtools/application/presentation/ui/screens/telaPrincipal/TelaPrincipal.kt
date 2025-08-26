package org.mdmsystemtools.application.presentation.ui.screens.telaPrincipal

import AppNavHost
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier, navController: NavHostController) {
	val startDestination = Destination.CADASTROS
	var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

	//TODO essa tela não deveria conter scaffold
	Scaffold(
		modifier = modifier,
		bottomBar = {
			NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
				Destination.entries.forEachIndexed { index, destination ->
					NavigationBarItem(
						selected = selectedDestination == index,
						onClick = {
							navController.navigate(route = destination.route)
							selectedDestination = index
						},
						icon = {
							Icon(
								destination.icon,
								contentDescription = destination.contentDescription
							)
						},
						label = { Text(destination.label) }
					)
				}
			}
		}
	) { contentPadding ->
		AppNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
	}
}
