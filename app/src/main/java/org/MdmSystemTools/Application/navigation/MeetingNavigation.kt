package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Meeting.MeetingDetailsScreen
import org.MdmSystemTools.Application.view.screens.Meeting.RollCallScreen
import org.MdmSystemTools.Application.view.screens.Meeting.Tabs

@Serializable internal object MeetingHistory

@Serializable internal data class MeetingRollcall(val tab: Tabs)

fun NavGraphBuilder.meetingHistory(onClickBack: () -> Unit, onClick: () -> Unit) {
  composable<MeetingHistory> { MeetingDetailsScreen(onClickBack, onClick) }
}

fun NavController.navigateToMeetingHistory() {
  navigate(MeetingHistory)
}

fun NavGraphBuilder.meetingRollCall(onClickBack: () -> Unit) {
  composable<MeetingRollcall> { navBackStackEntry ->
    val tab = navBackStackEntry.toRoute<MeetingRollcall>().tab
    RollCallScreen(
      initialTab = tab,
      onBack = onClickBack,
      onNavigateToStartGroup = {},
      onViewHistoryItem = {},
    )
  }
}

fun NavController.navigateToMeetingRollCall(tab: Tabs) {
  navigate(route = MeetingRollcall(tab))
}
