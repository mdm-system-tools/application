package org.MdmSystemTools.Application.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.MdmSystemTools.Application.view.screens.Auth.LoginScreen
import org.MdmSystemTools.Application.view.screens.Auth.RegisterScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Meeting.CalendarNavigation
import org.MdmSystemTools.Application.view.screens.Registration.FormScreen
import org.MdmSystemTools.Application.view.screens.Registration.ProfilesListScreen

@Composable
fun App(appState: AppState = rememberAppState()) {
	Scaffold(bottomBar = { BottomApp(navController = appState.navHostController) }) { innerPadding ->
		Route(appState = appState, modifier = Modifier.padding(innerPadding))
	}
}

@Composable
private fun Route(appState: AppState, modifier: Modifier) {
	NavHost(navController = appState.navHostController, startDestination = Screen.Login.route) {
		composable(Screen.Login.route) {
			LoginScreen(
				onNavigateToRegister = { appState.navigateToRegister() },
				onNavigateToDashboard = { appState.navigateToAssociate() })
		}
		composable(Screen.Register.route) {
			// TODO esse registro esta quebrado
			RegisterScreen(onNavigateToLogin = {
				appState.navigateToLogin()
			}, onRegisterSuccess = {
				appState.navigateToLogin()
			})
		}
		composable(Screen.Associate.route) {
			ProfilesListScreen(onClickBottom = {
				appState.navigateToForm()
			})
		}
		composable(Screen.Collaboration.route) {
			CollaboratorsScreen()
		}
		composable(Screen.Calendar.route) {
			CalendarNavigation()
		}
		composable(Screen.Form.route) {
			FormScreen()
		}
	}
}