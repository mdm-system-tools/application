package org.MdmSystemTools.Application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.MdmSystemTools.Application.view.BottomApp
import org.MdmSystemTools.Application.view.BottomAppBarItem
import org.MdmSystemTools.Application.view.ROUTES
import org.MdmSystemTools.Application.view.bottomAppBarItems
import org.MdmSystemTools.Application.view.screens.Auth.LoginScreen
import org.MdmSystemTools.Application.view.screens.Auth.RegisterScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Meeting.MeetingScreen
import org.MdmSystemTools.Application.view.screens.Registration.FormScreen
import org.MdmSystemTools.Application.view.screens.Registration.ProfilesListScreen

//TODO separar a estrutura de navegação
//TODO remover o botão da tela
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			val navController = rememberNavController()
			val backStackEntry by navController.currentBackStackEntryAsState()
			val currentDestination = backStackEntry?.destination
			MaterialTheme {
				Surface(
					modifier = Modifier,
					color = MaterialTheme.colorScheme.background
				) {
					val selectedItem by remember(currentDestination) {
						val item = currentDestination?.let { destination ->
							bottomAppBarItems.find {
								it.route == destination.route
							}
						} ?: bottomAppBarItems.first()
						mutableStateOf(item)
					}
					val isShowAppBar: Boolean = currentDestination?.let { destination ->
						bottomAppBarItems.find {
							it.route == destination.route
						}
					} != null
					App(
						bottomAppBarItemSelected = selectedItem,
						onBottomAppBarItemSelectedChange = {
							val route = it.route
							navController.navigate(route) {
								launchSingleTop = true
								popUpTo(route)
							}
						},
						onFabClick = {},
						showAppBar = isShowAppBar
					) {
						NavHost(
							navController = navController,
							startDestination = ROUTES.LOGIN.name
						) {
							composable(ROUTES.LOGIN.name) {
								LoginScreen(
									onNavigateToRegister = { navController.navigate(ROUTES.REGISTER.name) },
									onNavigateToDashboard = { navController.navigate(ROUTES.ASSOCIATE.name) })
							}
							composable(ROUTES.REGISTER.name) {
								// TODO esse registro esta quebrado
								RegisterScreen(onNavigateToLogin = {
									navController.popBackStack()
								}, onRegisterSuccess = {
								})
							}
							composable(ROUTES.ASSOCIATE.name) {
								ProfilesListScreen(onClickBottom = {
									navController.navigate(ROUTES.FORM.name)
								})
							}
							composable(ROUTES.COLLABORATION.name) {
								CollaboratorsScreen()
							}
							composable(ROUTES.CALENDAR.name) {
								MeetingScreen()
							}
							composable(ROUTES.FORM.name) {
								FormScreen()
							}
						}
					}
				}
			}
		}
	}
}

@Composable
fun App(
	bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
	onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
	onFabClick: () -> Unit = {},
	showAppBar: Boolean = false,
	content: @Composable () -> Unit
) {
	Scaffold(
		bottomBar = {
			if (showAppBar) {
				BottomApp(
					item = bottomAppBarItemSelected,
					items = bottomAppBarItems,
					onItemChange = onBottomAppBarItemSelectedChange,
				)
			}
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = onFabClick
			) {
				Icon(
					Icons.Filled.PointOfSale,
					contentDescription = null
				)
			}
		}
	) {
		Box(
			modifier = Modifier.padding(it)
		) {
			content()
		}
	}
}