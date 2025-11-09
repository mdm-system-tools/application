package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Meeting.MeetingDetailsScreen
import org.MdmSystemTools.Application.view.screens.Meeting.RollCallScreen

@Serializable internal object MeetingDetails

@Serializable internal object RollCall

fun NavGraphBuilder.meetingDetails(onClickBack: () -> Unit, onClick: () -> Unit) {
  composable<MeetingDetails> { MeetingDetailsScreen(onClickBack, onClick) }
}

fun NavController.navigateToMeetingDetails() {
  navigate(MeetingDetails)
}

fun NavGraphBuilder.meetingRollCall(onClickBack: () -> Unit) {
  composable<RollCall> { RollCallScreen(onClickBack = onClickBack) }
}

fun NavController.navigateToMeetingRollCall() {
  navigate(RollCall)
}
