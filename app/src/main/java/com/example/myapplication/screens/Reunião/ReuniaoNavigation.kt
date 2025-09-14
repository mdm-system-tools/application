package org.mdmsystemtools.application.presentation.ui.screens.Reunião

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.model.CalendarDateDto

enum class ReuniaoScreens {
	CALENDAR,
	ADD_EVENT
}

@Composable
fun ReuniaoNavigation(modifier: Modifier = Modifier) {
	var currentScreen by remember { mutableStateOf(ReuniaoScreens.CALENDAR) }
	var selectedDate by remember { mutableStateOf<CalendarDateDto?>(null) }
	val eventRepositoryImpl = EventRepositoryImpl.rememberEventManager()

	when (currentScreen) {
		ReuniaoScreens.CALENDAR -> {
			ReuniaoScreen(
				modifier = modifier,
				eventRepositoryImpl = eventRepositoryImpl,
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
					eventRepositoryImpl.adicionarEvento(evento)
					currentScreen = ReuniaoScreens.CALENDAR
				}
			)
		}
	}
}