package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Calendar.EventDetailsScreen
import org.MdmSystemTools.Application.view.screens.Calendar.EventFormScreen
import org.MdmSystemTools.Application.view.screens.Calendar.EventListScreen

@Serializable internal data class EventProfileDetails(val id: Int)

fun NavGraphBuilder.calendar(
  onClickEventProfile: (eventId: Int) -> Unit,
  onClickFloatingButton: () -> Unit,
) {
  composable<Route.Calendar> {
    EventListScreen(
      onClickEventProfile = { id -> onClickEventProfile(id) },
      onClickFloatingButton = onClickFloatingButton,
    )
  }
}

fun NavGraphBuilder.eventForm(onClickBackScreen: () -> Unit, onClickConfirmButton: () -> Unit) {
  composable<Route.EventForm> {
    EventFormScreen(onEventSaved = onClickConfirmButton, onNavigateBack = onClickBackScreen)
  }
}

fun NavGraphBuilder.eventProfileDetails(onClickBackScreen: () -> Unit) {
  composable<EventProfileDetails> { navBackStackEntry ->
    val id: Int = navBackStackEntry.toRoute<EventProfileDetails>().id
    EventDetailsScreen(id, onClickBackScreen)
  }
}

fun NavController.navigateToEventProfileDetails(id: Int) {
  navigate(route = EventProfileDetails(id = id))
}

fun NavController.navigateToEventForm() {
  navigate(route = Route.EventForm)
}
