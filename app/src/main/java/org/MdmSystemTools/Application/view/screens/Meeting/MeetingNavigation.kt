package org.MdmSystemTools.Application.view.screens.Meeting

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.viewmodel.Meeting.MeetingViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

enum class ReuniaoScreens {
	CALENDAR,
	ADD_EVENT
}

@Composable
fun CalendarNavigation(modifier: Modifier = Modifier, viewModel: MeetingViewModel = hiltViewModel()) {
	var currentScreen by remember { mutableStateOf(ReuniaoScreens.CALENDAR) }
	var selectedDate by remember { mutableStateOf<CalendarDateDto?>(null) }

	when (currentScreen) {
		ReuniaoScreens.CALENDAR -> {
			MeetingScreen(
				modifier = modifier,
				viewModel = viewModel,
				onNavigateToAddEvent = { date ->
					selectedDate = date
					currentScreen = ReuniaoScreens.ADD_EVENT
				}
			)
		}

		ReuniaoScreens.ADD_EVENT -> {
			BackHandler {
				currentScreen = ReuniaoScreens.CALENDAR
			}

			AdicionarEventoScreen(
				selectedDate = selectedDate,
				onNavigateBack = {
					currentScreen = ReuniaoScreens.CALENDAR
				},
				onEventSaved = { evento ->
					viewModel.adicionarEvento(evento)
					currentScreen = ReuniaoScreens.CALENDAR
				}
			)
		}
	}
}