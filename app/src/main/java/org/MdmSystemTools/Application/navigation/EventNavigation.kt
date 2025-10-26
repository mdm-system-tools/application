package org.MdmSystemTools.Application.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
internal data class EventProfileDetails(val id: String)

fun NavGraphBuilder.eventForm(
	onClickBackScreen: () -> Unit,
	onClickConfirmButton: () -> Unit
) {
	composable<Route.EventForm> {
	}
}

fun NavGraphBuilder.eventProfileDetails(onClickBackScreen: () -> Unit) {
	composable<EventProfileDetails> { navBackStackEntry ->
		val id: String = navBackStackEntry.toRoute<EventProfileDetails>().id
		Log.i("navegação", "chamada para event profile details id $id")
		// TODO: Criar EventProfileDetails screen
	}
}

fun NavController.navigateToEventProfileDetails(id: String) {
	navigate(route = EventProfileDetails(id = id))
}

fun NavController.navigateToEventForm() {
	navigate(route = Route.EventForm)
}
