package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Calendar.EventFormScreen

@Serializable internal data class EventProfileDetails(val id: String)

fun NavGraphBuilder.eventForm(onClickBackScreen: () -> Unit, onClickConfirmButton: () -> Unit) {
  composable<Route.EventForm> {
    EventFormScreen(onEventSaved = onClickConfirmButton, onNavigateBack = onClickBackScreen)
  }
}

fun NavGraphBuilder.eventProfileDetails(onClickBackScreen: () -> Unit) {
  composable<EventProfileDetails> { navBackStackEntry ->
    val id: String = navBackStackEntry.toRoute<EventProfileDetails>().id
    // TODO: Criar EventProfileDetails screen
    EventProfileDetails(id)
  }
}

fun NavController.navigateToEventProfileDetails(id: String) {
  navigate(route = EventProfileDetails(id = id))
}

fun NavController.navigateToEventForm() {
  navigate(route = Route.EventForm)
}
