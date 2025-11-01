package org.MdmSystemTools.Application.view.components.Common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.model.dto.EventDate
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.view.theme.AppConstants
import androidx.compose.ui.graphics.Color

@Composable
fun EventCard(event: EventDto, onClick: () -> Unit) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		onClick = onClick
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Row(
				modifier = Modifier,
				verticalAlignment = Alignment.CenterVertically,
			) {
				ColorIndicator(event.color)
				InformationToEvent(event)
			}
			Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
		}
	}
}

@Composable
private fun ColorIndicator(color: Color) {
	Box(
		modifier = Modifier
			.padding(all = 6.dp)
			.size(32.dp)
			.clip(CircleShape)
			.background(color)
	)
}

@Composable
private fun InformationToEvent(event: EventDto) {
	Column(Modifier.padding(start = 8.dp)) {
		Text(
			event.title,
			fontSize = AppConstants.FontSize.medium,
			fontWeight = FontWeight(500),
			maxLines = 1
		)
		Row {
			Text(
				"${event.date.day}/${event.date.month + 1}/${event.date.year}",
				fontSize = AppConstants.FontSize.small,
				fontWeight = FontWeight(400)
			)
			Spacer(modifier = Modifier.width(8.dp))
			Text(
				"${event.hourStart} - ${event.hourEnd}",
				fontSize = AppConstants.FontSize.small,
				fontWeight = FontWeight(400)
			)
		}
		if (event.groups != null) {
			Text(
				"Grupo: ${event.groups.name}",
				fontSize = AppConstants.FontSize.small,
				fontWeight = FontWeight(400)
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun EventCardPreview() {
	val sampleEvent = EventDto(
		id = "1",
		title = "Reunião de Planejamento",
		description = "Discussão sobre metas",
		date = EventDate(27, 9, 2025),
		hourStart = "09:00",
		hourEnd = "10:30",
		local = "Sala de Reuniões",
		region = "Norte",
		project = "Website",
		groups = GroupDto("1", "Desenvolvimento", Color(0xFF1C6AEA)),
		color = Color(0xFF1C6AEA)
	)
	EventCard(sampleEvent, {})
}
