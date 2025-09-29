package org.MdmSystemTools.Application.view.components.Meeting

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.model.DTO.EventDto
import org.MdmSystemTools.Application.viewmodel.Meeting.MeetingViewModel

@Composable
fun EventsList(
	viewModel: MeetingViewModel,
	month: Int,
	year: Int,
	onDeleteEvent: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	val eventos by viewModel.eventos.collectAsState()

	// Filtrar eventos do mês atual
	val eventosDoMes = remember(eventos, month, year) {
		eventos.filter { evento ->
			evento.data.month == month && evento.data.year == year
		}.sortedBy { it.data.day }
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
					text = "Eventos do mês",
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.padding(vertical = 8.dp)
				)
			}

			items(eventosDoMes, key = { it.id }) { evento ->
				EventCard(
					evento = evento,
					onDelete = { onDeleteEvent(evento.id) }
				)
			}
		}
	}
}