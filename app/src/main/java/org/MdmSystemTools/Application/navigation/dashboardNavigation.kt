package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.MdmSystemTools.Application.view.screens.Meeting.Tabs
import org.MdmSystemTools.Application.view.screens.Registration.Dashboard

fun NavGraphBuilder.dashboard(
	onClickMeetingBotton: (Tabs) -> Unit,
  onClickListRegisters: () -> Unit,
  onClickListEmployee: () -> Unit,
) {
  composable<BottomNav.Menu> {
    Dashboard(
      onClickMeetingBotton = onClickMeetingBotton,
      onClickListRegisters = onClickListRegisters,
      onClickListEmployee = onClickListEmployee,
    )
  }
}

fun NavController.navigateToDashboard() {
  navigate(route = BottomNav.Menu)
}
