package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Calendar.FormEventScreen

@Serializable
object AddEvent

fun NavGraphBuilder.addEvent() {
	composable<AddEvent> {
		//TODO adicionar onEventSaved
		FormEventScreen(
			onNavigateBack = {
			},
		)
	}
}

