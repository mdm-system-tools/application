package org.MdmSystemTools.Application.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import org.MdmSystemTools.Application.view.App
import org.MdmSystemTools.Application.view.RouteObj
import org.MdmSystemTools.Application.view.screens.Auth.SignInScreen
import org.MdmSystemTools.Application.view.screens.Auth.SignUpScreen
import org.MdmSystemTools.Application.view.screens.Calendar.CalendarScreen
import org.MdmSystemTools.Application.view.screens.Calendar.FormEventScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen
import org.MdmSystemTools.Application.view.screens.Registration.FormAssociateScreen


@Composable
fun StartApp(
	appState: AppState = rememberAppState()
) {
	val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination
	Surface {
		App(
			currentDestination = currentDestination,
			navigateToTopLevelDestination = appState::navigateTo
		) {
			CreateNavHost(appState)
		}
	}
}

@Composable
private fun CreateNavHost(appState: AppState) {
	NavHost(
		navController = appState.navHostController,
		startDestination = RouteObj.Associate
	) {
		composable(Route.Login.destination) {
			SignInScreen(
				onNavigateToRegister = { appState::navigateToRegister },
				onNavigateToDashboard = { appState::navigateToAssociate }
			)
		}

		composable<RouteObj.Associate> {
			AssociateListScreen(modifier = Modifier)
		}

		mainNavigation(appState)
	}
}

private fun NavGraphBuilder.mainNavigation(appState: AppState) {
	val route = "main"
	navigation(startDestination = Route.Associate.destination, route = route) {
		composable(Route.Register.destination) {
			// TODO esse registro esta quebrado
			SignUpScreen(onNavigateToLogin = {
				appState::navigateToLogin
			}, onRegisterSuccess = {
				appState::navigateToLogin
			})
		}

		composable<RouteObj.Collaboration> {
			CollaboratorsScreen()
		}

		composable<RouteObj.Calendar> {
			CalendarScreen(
				onNavigateToAddEvent = {
					appState::navigateToEventForm
				})
		}

		composable(Route.Form.destination) {
			FormAssociateScreen(
				onClick = {
					appState::navigateToAssociate
				})
		}

		composable(Route.AddEvent.destination) {
			//TODO adicionar onEventSaved
			FormEventScreen(
				onNavigateBack = {
					appState::navigateToCalendar
				},
			)
		}
	}
}
