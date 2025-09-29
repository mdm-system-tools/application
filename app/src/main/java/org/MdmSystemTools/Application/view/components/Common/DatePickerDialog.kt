package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.view.components.Meeting.ModernCalendar

@Composable
fun DatePickerDialog(
	selectedDate: CalendarDateDto,
	onDateSelected: (CalendarDateDto) -> Unit,
	onDismiss: () -> Unit
) {
	var currentMonth by remember { mutableIntStateOf(selectedDate.month) }
	var currentYear by remember { mutableIntStateOf(selectedDate.year) }
	var selectedDay by remember { mutableIntStateOf(selectedDate.day) }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = {
			Text(
				text = "Selecionar data",
				fontWeight = FontWeight.SemiBold
			)
		},
		text = {
			// Calendário moderno
			ModernCalendar(
				currentMonth = currentMonth,
				currentYear = currentYear,
				onDateClick = { date ->
					selectedDay = date.day
					onDateSelected(CalendarDateDto(selectedDay, currentMonth, currentYear, true))
					onDismiss()
				},
				onMonthChange = { month, year ->
					currentMonth = month
					currentYear = year
				},
				showHeader = false,
				modifier = Modifier.height(300.dp)
			)
		},
		confirmButton = {
			TextButton(
				onClick = {
					onDateSelected(CalendarDateDto(selectedDay, currentMonth, currentYear, isCurrentMonth = true))
				}
			) {
				Text("OK")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Cancelar")
			}
		}
	)
}