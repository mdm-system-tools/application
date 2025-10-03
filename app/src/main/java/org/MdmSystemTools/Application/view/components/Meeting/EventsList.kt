package org.MdmSystemTools.Application.view.components.Meeting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.model.DTO.EventDto
import org.MdmSystemTools.Application.viewmodel.Meeting.MeetingViewModel
import org.MdmSystemTools.Application.view.theme.AppConstants

@Composable
fun EventsList(
	viewModel: MeetingViewModel,
	month: Int,
	year: Int,
	onDeleteEvent: (String) -> Unit,
	onEditEvent: (EventDto) -> Unit = { },
	modifier: Modifier = Modifier
) {
	val eventos by viewModel.eventos.collectAsState()
	var eventoSelecionado by remember { mutableStateOf<EventDto?>(null) }

	// Filtrar eventos do mês atual
	val eventosDoMes = remember(eventos, month, year) {
		eventos.filter { evento ->
			evento.date.month == month && evento.date.year == year
		}.sortedBy { it.date.day }
	}

	if (eventosDoMes.isNotEmpty()) {
		LazyColumn(
			modifier = modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			item {
				Text(
					text = AppConstants.Strings.meetingsOfMonth,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.padding(vertical = AppConstants.Spacing.small)
				)
			}

			items(eventosDoMes, key = { it.id }) { evento ->
				EventCard(
					evento = evento,
					onDelete = { onDeleteEvent(evento.id) },
					onEdit = { onEditEvent(evento) },
					onClick = { eventoSelecionado = evento }
				)
			}
		}
	}

	// Diálogo de detalhes do evento
	eventoSelecionado?.let { evento ->
		EventDetailsDialog(
			evento = evento,
			onDismiss = { eventoSelecionado = null }
		)
	}
}