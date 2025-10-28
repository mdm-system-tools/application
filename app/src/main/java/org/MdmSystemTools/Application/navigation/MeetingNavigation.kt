package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Meeting.MeetingDetails
import org.MdmSystemTools.Application.view.screens.Meeting.RollCall

@Serializable internal object MeetingDetails
@Serializable internal object RollCall

fun NavGraphBuilder.meetingDetails(onClickBack: () -> Unit, onClick: () -> Unit) {
	composable<MeetingDetails> { MeetingDetails(onClickBack, onClick) }
}

fun NavController.navigateToMeetingDetails() {
	navigate(MeetingDetails)
}

fun NavGraphBuilder.meetingRollCall(onClickBack: () -> Unit) {
	composable<RollCall> { RollCall(onClickBack = onClickBack) }
}

fun NavController.navigateToMeetingRollCall() {
	navigate(RollCall)
}
