package org.MdmSystemTools.Application.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.MdmSystemTools.Application.view.screens.Auth.SignInScreen
import org.MdmSystemTools.Application.view.screens.Auth.SignUpScreen
import org.MdmSystemTools.Application.view.screens.Calendar.CalendarScreen
import org.MdmSystemTools.Application.view.screens.Calendar.FormEventScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen
import org.MdmSystemTools.Application.view.screens.Registration.FormAssociateScreen

@Composable
fun App(appState: AppState = rememberAppState()) {
	val navDestination = appState.getCurrentDestination()
	val currentItem = appState.getCurrentBottomBarItem()

	Scaffold(
		topBar = TopBarFactory.make(
			navDestination
		),
		bottomBar = BottomBarFactory.make(
			navDestination,
			currentItem,
			appState.navHostController
		),
		floatingActionButton = FloatingButtonFactory.make(
			navDestination,
			appState::navigateToEventForm
		)
	) { innerPadding ->
		Route(
			appState = appState, modifier = Modifier.padding(innerPadding)
		)
	}
}

@Composable
private fun Route(appState: AppState, modifier: Modifier) {
	NavHost(navController = appState.navHostController, startDestination = Screen.Associate.route) {
		composable(Screen.Login.route) {
			SignInScreen(
				onNavigateToRegister = { appState.navigateToRegister() },
				onNavigateToDashboard = { appState.navigateToAssociate() })
		}

		composable(Screen.Register.route) {
			// TODO esse registro esta quebrado
			SignUpScreen(onNavigateToLogin = {
				appState.navigateToLogin()
			}, onRegisterSuccess = {
				appState.navigateToLogin()
			})
		}

		composable(Screen.Associate.route) {
			AssociateListScreen(modifier = modifier)
		}

		composable(Screen.Collaboration.route) {
			CollaboratorsScreen()
		}

		composable(Screen.Calendar.route) {
			CalendarScreen(
				onNavigateToAddEvent = {
					appState.navigateToEventForm()
				})
		}

		composable(Screen.Form.route) {
			FormAssociateScreen(
				onClick = {
					appState.navigateToAssociate()
				})
		}

		composable(Screen.AddEvent.route) {
			//TODO adicionar onEventSaved
			FormEventScreen(
				onNavigateBack = {
					appState.navigateToCalendar()
				},
			)
		}
	}
}