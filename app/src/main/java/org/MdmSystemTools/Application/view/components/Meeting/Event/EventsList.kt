package org.MdmSystemTools.Application.view.components.Meeting.Event

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.flow.StateFlow
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.view.theme.AppConstants

@Composable
fun EventsList(
	events: StateFlow<List<EventDto>>,
	month: Int,
	year: Int,
	onDeleteEvent: (String) -> Unit,
	onEditEvent: (EventDto) -> Unit = { },
	modifier: Modifier = Modifier
) {
	val eventsList by events.collectAsState()
	var selectedEvent by remember { mutableStateOf<EventDto?>(null) }

	// Filtrar eventos do mês atual
	val monthEvents = remember(eventsList, month, year) {
		eventsList.filter { event ->
			event.date.month == month && event.date.year == year
		}.sortedBy { it.date.day }
	}

	if (monthEvents.isNotEmpty()) {
		LazyColumn(
			modifier = modifier
				.fillMaxWidth()
				.padding(horizontal = AppConstants.Spacing.medium),
			verticalArrangement = Arrangement.spacedBy(AppConstants.Spacing.small)
		) {
			item {
				Text(
					text = AppConstants.Strings.meetingsOfMonth,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.padding(vertical = AppConstants.Spacing.small)
				)
			}

			items(monthEvents, key = { it.id }) { event ->
				EventCard(
					event = event,
					onDelete = { onDeleteEvent(event.id) },
					onEdit = { onEditEvent(event) },
					onClick = { selectedEvent = event }
				)
			}
		}
	}

	// Diálogo de detalhes do evento
	selectedEvent?.let { event ->
		EventDetailsDialog(
			event = event,
			onDismiss = { selectedEvent = null }
		)
	}
}