package org.MdmSystemTools.Application.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.MdmSystemTools.Application.view.components.Registration.SearchBar
import org.MdmSystemTools.Application.view.screens.Auth.SignInScreen
import org.MdmSystemTools.Application.view.screens.Auth.SignUpScreen
import org.MdmSystemTools.Application.view.screens.Calendar.CalendarScreen
import org.MdmSystemTools.Application.view.screens.Calendar.FormEventScreen
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen
import org.MdmSystemTools.Application.view.screens.Registration.FormAssociateScreen
import org.MdmSystemTools.Application.view.theme.AppConstants

@Composable
fun App(appState: AppState = rememberAppState()) {
	var showBottomBar by remember { mutableStateOf(false) }
	val isShowBottomBar = appState.shouldShowBottomBar()

	var showFloatingBottom by remember { mutableStateOf(false) }
	val isShowFloatingBottom = appState.shouldShowFloatingBottom()

	Scaffold(
		topBar = {
			if (isShowFloatingBottom) {
				SearchBar(placeholder = "Pesquisar cadastros...")
			}
		},
		bottomBar = {
			if (isShowBottomBar) {
				BottomApp(
					itemSelected = appState.getCurrentBottomBarItem(),
					navController = appState.navHostController
				)
			}
		},
		floatingActionButton = {
			if (isShowFloatingBottom) {
				FloatingActionButton(
					onClick = { appState.navigateToAssociateForm() },
					shape = CircleShape,
					containerColor = MaterialTheme.colorScheme.primary,
				) {
					Text(
						text = "+", fontSize = 28.sp, textAlign = TextAlign.Center
					)
				}
			}
		},
	) { innerPadding ->
		Route(
			appState = appState, modifier = Modifier.padding(innerPadding)
		)
	}
}

@Composable
private fun Route(appState: AppState, modifier: Modifier) {
	NavHost(navController = appState.navHostController, startDestination = Screen.Associate.route) {
		composable(
			Screen.Login.route,
			enterTransition = {
				fadeIn(
					animationSpec = tween(
						300, easing = LinearEasing
					)
				) + slideIntoContainer(
					animationSpec = tween(300, easing = EaseIn),
					towards = AnimatedContentTransitionScope.SlideDirection.Start
				)
			},
		) {
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

		composable(Screen.Form.route, enterTransition = {
			slideInHorizontally(
				initialOffsetX = { it },
				animationSpec = tween(AppConstants.Animation.defaultDurationMs, easing = EaseOutCubic)
			) + fadeIn(animationSpec = tween(AppConstants.Animation.defaultDurationMs))
		}, exitTransition = {
			slideOutHorizontally(
				targetOffsetX = { it },
				animationSpec = tween(AppConstants.Animation.defaultDurationMs, easing = EaseInCubic)
			) + fadeOut(animationSpec = tween(AppConstants.Animation.defaultDurationMs))
		}

		) {
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