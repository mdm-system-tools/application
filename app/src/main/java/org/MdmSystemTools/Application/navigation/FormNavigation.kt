package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Calendar.FormEventScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateFormScreen

@Serializable
object AddEvent

@Serializable
object Form

fun NavGraphBuilder.form() {
	composable<Form> {
		AssociateFormScreen(
			onClickConfirmButton = {
				//appState::navigateToAssociate
			})
	}
}

fun NavGraphBuilder.addEvent() {
	composable<AddEvent> {
		//TODO adicionar onEventSaved
		FormEventScreen(
			onNavigateBack = {
				//	appState::navigateToCalendar
			},
		)
	}
}

