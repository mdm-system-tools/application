package org.MdmSystemTools.Application.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import org.MdmSystemTools.Application.view.screens.Auth.SignInScreen
import org.MdmSystemTools.Application.view.screens.Auth.SignUpScreen
import org.MdmSystemTools.Application.view.screens.Calendar.FormEventScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Calendar.CalendarScreen
import org.MdmSystemTools.Application.view.screens.Registration.FormAssociateScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen

@Composable
fun App(appState: AppState = rememberAppState()) {
	var showBottomBar by remember { mutableStateOf(false) }
	val isShowBottomBar = appState.shouldShowBottomBar()

	LaunchedEffect(isShowBottomBar) {
		if (isShowBottomBar) {
			delay(100)
			showBottomBar = true
		} else {
			showBottomBar = false
		}
	}

	Scaffold(
		bottomBar = {
			AnimatedVisibility(
				visible = showBottomBar,
				enter = fadeIn(animationSpec = tween(150)),
				exit = fadeOut(animationSpec = tween(100))
			) {
				BottomApp(
					itemSelected = appState.getCurrentBottomBarItem(),
					navController = appState.navHostController
				)
			}
		}
	) { innerPadding ->
		Route(
			appState = appState,
			modifier = Modifier.padding(innerPadding)
		)
	}
}

@Composable
private fun Route(appState: AppState, modifier: Modifier) {
	NavHost(navController = appState.navHostController, startDestination = Screen.Calendar.route) {
		composable(Screen.Login.route) {
			SignInScreen(
				onNavigateToRegister = { appState.navigateToRegister() },
				onNavigateToDashboard = { appState.navigateToAssociate() }
			)
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
			AssociateListScreen(onClickFloatingBottom = {
				appState.navigateToForm()
			})
		}

		composable(Screen.Collaboration.route) {
			CollaboratorsScreen()
		}

		composable(Screen.Calendar.route) {
			CalendarScreen(
				onNavigateToAddEvent = {
					appState.navigateToAddEvent()
				}
			)
		}

		composable(Screen.Form.route) {
			FormAssociateScreen(onClick = {
				appState.navHostController.popBackStack()
			})
		}

		composable(Screen.AddEvent.route) {
			FormEventScreen(
				onNavigateBack = {
					appState.navigateToCalendar()
				},
			)
		}
	}
}