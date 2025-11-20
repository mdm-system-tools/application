package org.MdmSystemTools.Application.view.screens.Meeting

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
internal object MeetingHistory

@Serializable
internal data class MeetingRollcall(val tab: TabsForRollCall)

fun NavGraphBuilder.meetingHistory(onClickBack: () -> Unit, onClick: () -> Unit) {
	composable<MeetingHistory> { MeetingDetailsScreen(onClickBack, onClick) }
}

fun NavController.navigateToMeetingHistory() {
	navigate(MeetingHistory)
}

fun NavGraphBuilder.meetingRollCall(onClickBack: () -> Unit) {
	composable<MeetingRollcall> { navBackStackEntry ->
		val tab = navBackStackEntry.toRoute<MeetingRollcall>().tab
		Log.i("Navigation", "rollcallScreen Tab: $tab")
		RollCallScreen(
			initialTab = tab,
			onBack = onClickBack,
			onNavigateToStartGroup = {},
			onViewHistoryItem = {},
		)
	}
}

fun NavController.navigateToMeetingRollCall(tab: TabsForRollCall) {
	navigate(route = MeetingRollcall(tab))
}
