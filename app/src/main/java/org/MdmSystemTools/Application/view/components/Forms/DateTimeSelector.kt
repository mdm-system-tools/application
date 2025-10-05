package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateTimeSelector(
	selectedDate: Triple<Int, Int, Int>,
	startTime: String,
	endTime: String,
	onDateClick: () -> Unit,
	onStartTimeClick: () -> Unit,
	onEndTimeClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(12.dp)
	) {
		// Data
		DateTimeCard(
			modifier = Modifier.weight(1f),
			label = "Data",
			value = formatDate(selectedDate),
			icon = Icons.Default.CalendarToday,
			onClick = onDateClick
		)

		// Horário
		Column(modifier = Modifier.weight(1f)) {
			TimeCard(
				label = "Início",
				time = startTime,
				onClick = onStartTimeClick
			)

			Spacer(modifier = Modifier.height(8.dp))

			TimeCard(
				label = "Fim",
				time = endTime,
				onClick = onEndTimeClick
			)
		}
	}
}

@Composable
private fun DateTimeCard(
	modifier: Modifier = Modifier,
	label: String,
	value: String,
	icon: ImageVector,
	onClick: () -> Unit
) {
	Card(
		modifier = modifier
			.clip(RoundedCornerShape(12.dp))
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
		)
	) {
		Column(
			modifier = Modifier.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Icon(
				imageVector = icon,
				contentDescription = label,
				tint = MaterialTheme.colorScheme.primary,
				modifier = Modifier.size(24.dp)
			)

			Spacer(modifier = Modifier.height(8.dp))

			Text(
				text = label,
				fontSize = 12.sp,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
			)

			Text(
				text = value,
				fontSize = 16.sp,
				fontWeight = FontWeight.SemiBold,
				color = MaterialTheme.colorScheme.onSurface
			)
		}
	}
}

@Composable
private fun TimeCard(
	label: String,
	time: String,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clip(RoundedCornerShape(12.dp))
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Icon(
					Icons.Default.Schedule,
					contentDescription = label,
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier.size(20.dp)
				)
				Text(
					text = label,
					fontSize = 14.sp,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
				)
			}

			Text(
				text = time,
				fontSize = 16.sp,
				fontWeight = FontWeight.SemiBold,
				color = MaterialTheme.colorScheme.onSurface
			)
		}
	}
}

private fun formatDate(date: Triple<Int, Int, Int>): String {
	val (day, month, _) = date
	val monthNames = listOf(
		"Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
		"Jul", "Ago", "Set", "Out", "Nov", "Dez"
	)
	return "$day ${monthNames[month]}"
}