package org.MdmSystemTools.Application.view.components.Meeting.Calendar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.MdmSystemTools.Application.model.dto.CalendarDataDto
import org.MdmSystemTools.Application.model.utils.calculateCalendarData
import org.MdmSystemTools.Application.model.utils.getToday

@Composable
fun Calendar(
	currentMonth: Int,
	currentYear: Int,
	selectedDate: Triple<Int, Int, Int>? = null,
	showHeader: Boolean = true,
	calendarDataDto: CalendarDataDto,
	today: Triple<Int, Int, Int>,
	onDateClick: (day: Int, month: Int, year: Int) -> Unit = { _, _, _ -> },
	onMonthChange: (month: Int, year: Int) -> Unit = { _, _ -> },
	hasEventsCallback: (day: Int, month: Int, year: Int) -> Boolean = { _, _, _ -> false },
	eventCountCallback: (day: Int, month: Int, year: Int) -> Int = { _, _, _ -> 0 },
	modifier: Modifier = Modifier
) {
	Column(modifier = modifier) {
		if (showHeader) {
			MonthTitle(
				month = currentMonth,
				year = currentYear
			)
		}


		AnimatedContent(
			targetState = Pair(currentMonth, currentYear),
			transitionSpec = {
				val isMovingForward = targetState.first > initialState.first ||
						(targetState.first == 0 && initialState.first == 11)

				(slideInVertically(
					animationSpec = spring(
						dampingRatio = Spring.DampingRatioMediumBouncy,
						stiffness = Spring.StiffnessLow
					)
				) { height -> if (isMovingForward) height / 3 else -height / 3 } +
				fadeIn(animationSpec = tween(300))) togetherWith
				(slideOutVertically(
					animationSpec = spring(
						dampingRatio = Spring.DampingRatioMediumBouncy,
						stiffness = Spring.StiffnessLow
					)
				) { height -> if (isMovingForward) -height / 3 else height / 3 } +
				fadeOut(animationSpec = tween(200)))
			},
			label = "calendar_transition"
		) { (month, year) ->
			CalendarGrid(
				month = month,
				year = year,
				selectedDate = selectedDate,
				calendarDataDto = calendarDataDto,
				today = today,
				onDateClick = onDateClick,
				onMonthChange = onMonthChange,
				hasEventsCallback = hasEventsCallback,
				eventCountCallback = eventCountCallback
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun CalendarPreview() {
	MaterialTheme {
		Calendar(
			currentMonth = 10,
			currentYear = 2025,
			selectedDate = Triple(15, 10, 2025),
			showHeader = true,
			calendarDataDto = calculateCalendarData(10, 2025),
			today = getToday(),
			hasEventsCallback = { day, _, _ ->
				day in listOf(4, 10, 15, 20, 25)
			},
			eventCountCallback = { day, _, _ ->
				when (day) {
					4 -> 1
					10 -> 2
					15 -> 3
					20 -> 5
					25 -> 2
					else -> 0
				}
			}
		)
	}
}
