package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.model.utils.calculateCalendarData
import org.MdmSystemTools.Application.model.utils.getToday
import org.MdmSystemTools.Application.view.components.Meeting.Calendar.Calendar

@Composable
fun DatePickerDialog(
	selectedDate: Triple<Int, Int, Int>,
	onDateSelected: (Triple<Int, Int, Int>) -> Unit,
	onDismiss: () -> Unit
) {
	val (day, month, year) = selectedDate
	var currentMonth by remember { mutableIntStateOf(month) }
	var currentYear by remember { mutableIntStateOf(year) }
	var selectedDay by remember { mutableIntStateOf(day) }

	val calendarData = remember(currentMonth, currentYear) {
		calculateCalendarData(currentMonth, currentYear)
	}
	val today = remember { getToday() }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = {
			Text(
				text = "Selecionar data",
				fontWeight = FontWeight.SemiBold
			)
		},
		text = {
			Calendar(
				currentMonth = currentMonth,
				currentYear = currentYear,
				showHeader = false,
				calendarData = calendarData,
				today = today,
				onDateClick = { clickedDay, clickedMonth, clickedYear ->
					selectedDay = clickedDay
					onDateSelected(Triple(selectedDay, clickedMonth, clickedYear))
					onDismiss()
				},
				onMonthChange = { newMonth, newYear ->
					currentMonth = newMonth
					currentYear = newYear
				},
				modifier = Modifier.height(300.dp)
			)
		},
		confirmButton = {
			TextButton(
				onClick = {
					onDateSelected(Triple(selectedDay, currentMonth, currentYear))
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