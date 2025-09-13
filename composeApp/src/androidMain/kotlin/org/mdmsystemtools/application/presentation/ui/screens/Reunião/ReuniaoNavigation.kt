package org.mdmsystemtools.application.presentation.ui.screens.Reunião

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

enum class ReuniaoScreens {
	CALENDAR,
	ADD_EVENT
}

@Composable
fun ReuniaoNavigation(modifier: Modifier = Modifier) {
	var currentScreen by remember { mutableStateOf(ReuniaoScreens.CALENDAR) }
	var selectedDate by remember { mutableStateOf<CalendarDate?>(null) }
	val eventManager = EventManager.rememberEventManager()

	when (currentScreen) {
		ReuniaoScreens.CALENDAR -> {
			ReuniaoScreen(
				modifier = modifier,
				eventManager = eventManager,
				onNavigateToAddEvent = { date ->
					selectedDate = date
					currentScreen = ReuniaoScreens.ADD_EVENT
				}
			)
		}
		ReuniaoScreens.ADD_EVENT -> {
			AdicionarEventoScreen(
				selectedDate = selectedDate,
				onNavigateBack = {
					currentScreen = ReuniaoScreens.CALENDAR
				},
				onEventSaved = { evento ->
					eventManager.adicionarEvento(evento)
					currentScreen = ReuniaoScreens.CALENDAR
				}
			)
		}
	}
}