package org.MdmSystemTools.Application.view.screens.Meeting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.viewmodel.Meeting.MeetingViewModel
import org.MdmSystemTools.Application.view.components.Common.ButtonFormAdd
import org.MdmSystemTools.Application.view.components.Meeting.ModernCalendar
import org.MdmSystemTools.Application.view.components.Meeting.MonthTitle
import org.MdmSystemTools.Application.view.components.Meeting.EventsList
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import java.util.*

@Composable
fun ReuniaoScreen(
	modifier: Modifier = Modifier,
	viewModel: MeetingViewModel = hiltViewModel(),
	onNavigateToAddEvent: ((CalendarDateDto) -> Unit)? = null
) {
	val currentMonth by viewModel.currentMonth.collectAsState()
	val currentYear by viewModel.currentYear.collectAsState()
	val eventos by viewModel.eventos.collectAsState()

	// Estado para controlar a data selecionada (double-click)
	var selectedDate by remember { mutableStateOf<CalendarDateDto?>(null) }

	Box(modifier = modifier.fillMaxSize()) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(top = 16.dp)
		) {
			// Título do mês/ano
			MonthTitle(
				month = currentMonth,
				year = currentYear
			)

			// Calendário com double-click
			ModernCalendar(
				currentMonth = currentMonth,
				currentYear = currentYear,
				selectedDate = selectedDate,
				onDateClick = { date ->
					handleDateClick(
						date = date,
						selectedDate = selectedDate,
						onDateSelected = { selectedDate = it },
						onNavigateToAddEvent = onNavigateToAddEvent
					)
				},
				onMonthChange = { month, year ->
					viewModel.navegarParaMes(month, year)
					selectedDate = null // Limpar seleção ao mudar de mês
				},
				hasEventsCallback = { date -> viewModel.temEventosNaData(date) },
				eventCountCallback = { date -> viewModel.obterEventosPorData(date).size },
				showHeader = false
			)

			// Lista de eventos do mês
			EventsList(
				viewModel = viewModel,
				month = currentMonth,
				year = currentYear,
				onDeleteEvent = { eventId -> viewModel.removerEvento(eventId) },
				onEditEvent = { evento ->
					// TODO: Implementar navegação para tela de edição
					// Por enquanto, apenas um placeholder
				}
			)
		}

		// Botão flutuante se não há eventos no mês
		ShowFloatingButtonIfNeeded(
			hasEvents = eventos.any {
				it.data.month == currentMonth && it.data.year == currentYear
			},
			onNavigateToAddEvent = onNavigateToAddEvent
		)
	}
}

// Função auxiliar para lidar com cliques (double-click)
private fun handleDateClick(
	date: CalendarDateDto,
	selectedDate: CalendarDateDto?,
	onDateSelected: (CalendarDateDto?) -> Unit,
	onNavigateToAddEvent: ((CalendarDateDto) -> Unit)?
) {
	if (selectedDate?.day == date.day &&
		selectedDate.month == date.month &&
		selectedDate.year == date.year) {
		// Double click - navegar para adicionar evento
		onNavigateToAddEvent?.invoke(date)
	} else {
		// Single click - apenas selecionar a data
		onDateSelected(date)
	}
}

// Componente para mostrar botão flutuante condicionalmente
@Composable
private fun ShowFloatingButtonIfNeeded(
	hasEvents: Boolean,
	onNavigateToAddEvent: ((CalendarDateDto) -> Unit)?
) {
	if (!hasEvents) {
		ButtonFormAdd(
			onClick = {
				// Adicionar evento para hoje por padrão
				val nowCalendar = Calendar.getInstance()
				val todayDate = CalendarDateDto(
					nowCalendar.get(Calendar.DAY_OF_MONTH),
					nowCalendar.get(Calendar.MONTH),
					nowCalendar.get(Calendar.YEAR),
					true
				)
				onNavigateToAddEvent?.invoke(todayDate)
			}
		)
	}
}
