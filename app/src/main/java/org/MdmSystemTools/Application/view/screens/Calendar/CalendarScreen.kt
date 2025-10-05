package org.MdmSystemTools.Application.view.screens.Calendar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dagger.hilt.android.EntryPointAccessors
import org.MdmSystemTools.Application.MyApp
import org.MdmSystemTools.Application.model.DTO.CalendarConfigDto
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.repository.CalendarRepository
import org.MdmSystemTools.Application.model.repository.EventRepository
import org.MdmSystemTools.Application.view.components.Common.ButtonFormAdd
import org.MdmSystemTools.Application.view.components.Meeting.Calendar.Calendar
import org.MdmSystemTools.Application.view.components.Meeting.Calendar.MonthTitle
import org.MdmSystemTools.Application.view.components.Meeting.Event.EventsList
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CalendarScreenEntryPoint {
	fun calendarRepository(): CalendarRepository
	fun eventRepository(): EventRepository
}

@Composable
//TODO o botão de adicionar não funciona pois não possui implementação de navegação no navController
fun CalendarScreen(
	modifier: Modifier = Modifier,
	onNavigateToAddEvent: () -> Unit
) {
	val context = LocalContext.current
	val appContext = context.applicationContext as MyApp
	val entryPoint = EntryPointAccessors.fromApplication(
		appContext,
		CalendarScreenEntryPoint::class.java
	)

	val calendarRepository = remember { entryPoint.calendarRepository() }
	val eventRepository = remember { entryPoint.eventRepository() }

	val currentMonth by calendarRepository.currentMonth.collectAsState()
	val currentYear by calendarRepository.currentYear.collectAsState()
	val events by eventRepository.events.collectAsState()
	val calendarData by calendarRepository.calendarData.collectAsState()
	val today by calendarRepository.today.collectAsState()

	var selectedDate by remember { mutableStateOf<CalendarDateDto?>(null) }

	Box(modifier = modifier.fillMaxSize()) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(top = 16.dp)
		) {
			MonthTitle(
				month = currentMonth,
				year = currentYear
			)

			// Calendário com double-click
			Calendar(
				config = CalendarConfigDto(
					currentMonth = currentMonth,
					currentYear = currentYear,
					selectedDate = selectedDate,
					showHeader = false
				),
				calendarData = calendarData,
				today = today,
				onDateClick = { date ->
					handleDateClick(
						date = date,
						selectedDate = selectedDate,
						onDateSelected = { selectedDate = it },
						onNavigateToAddEvent = onNavigateToAddEvent
					)
				},
				onMonthChange = { month, year ->
					calendarRepository.navigateToMonth(month, year)
					selectedDate = null // Limpar seleção ao mudar de mês
				},
				hasEventsCallback = { date -> eventRepository.hasEventsOnDate(date) },
				eventCountCallback = { date -> eventRepository.getEventsByDate(date).size }
			)

			// Lista de eventos do mês
			EventsList(
				events = eventRepository.events,
				month = currentMonth,
				year = currentYear,
				onDeleteEvent = { eventId -> eventRepository.removeEvent(eventId) },
				onEditEvent = { event ->
					// TODO: Implementar navegação para tela de edição
					// Por enquanto, apenas um placeholder
				}
			)
		}

		// Botão flutuante se não há eventos no mês
		ShowFloatingButtonIfNeeded(
			hasEvents = events.any { event ->
				event.date.month == currentMonth && event.date.year == currentYear
			},
			onClick = onNavigateToAddEvent
		)
	}
}

// Função auxiliar para lidar com cliques (double-click)
private fun handleDateClick(
	date: CalendarDateDto,
	selectedDate: CalendarDateDto?,
	onDateSelected: (CalendarDateDto?) -> Unit,
	onNavigateToAddEvent: () -> Unit
) {
	if (selectedDate?.day == date.day &&
		selectedDate.month == date.month &&
		selectedDate.year == date.year) {
		// Double click - navegar para adicionar evento
		onNavigateToAddEvent
	} else {
		// Single click - apenas selecionar a data
		onDateSelected(date)
	}
}

// Componente para mostrar botão flutuante condicionalmente
@Composable
private fun ShowFloatingButtonIfNeeded(
	hasEvents: Boolean,
	onClick: () -> Unit,
) {
	if (!hasEvents) {
		ButtonFormAdd(
			onClick = onClick
			//	{
				//// Adicionar evento para hoje por padrão
				//val nowCalendar = Calendar.getInstance()
				//val todayDate = CalendarDateDto(
				//	nowCalendar.get(Calendar.DAY_OF_MONTH),
				//	nowCalendar.get(Calendar.MONTH),
				//	nowCalendar.get(Calendar.YEAR),
				//	true
				//)
			//}
		)
	}
}
