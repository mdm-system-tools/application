package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Dashboard
import org.MdmSystemTools.Application.view.screens.Meeting.TabsForRollCall

@Serializable
data object Dashboard
fun NavGraphBuilder.dashboard(
	onClickMeetingBotton: (TabsForRollCall) -> Unit,
	onClickListRegisters: () -> Unit,
	onClickListEmployee: () -> Unit,
) {
  composable<Dashboard> {
    Dashboard(
      onClickMeetingBotton = onClickMeetingBotton,
      onClickListRegisters = onClickListRegisters,
      onClickListEmployee = onClickListEmployee,
    )
  }
}

fun NavController.navigateToDashboard() {
  navigate(Dashboard)
}