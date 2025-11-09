package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.MdmSystemTools.Application.view.screens.Registration.DashBoard

fun NavGraphBuilder.menu(onClickViewMeeting: () -> Unit) {
  composable<BottomNav.Menu> { DashBoard(onClickViewMeeting) }
}

fun NavController.navigateToMenu() {
  navigate(route = BottomNav.Menu)
}
