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
import org.MdmSystemTools.Application.model.DTO.CalendarConfigDto
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto

@Composable
fun Calendar(
	config: CalendarConfigDto,
	calendarData: org.MdmSystemTools.Application.view.components.Meeting.Calendar.CalendarData,
	today: Triple<Int, Int, Int>,
	onDateClick: (CalendarDateDto) -> Unit = {},
	onMonthChange: (month: Int, year: Int) -> Unit = { _, _ -> },
	hasEventsCallback: (CalendarDateDto) -> Boolean = { false },
	eventCountCallback: (CalendarDateDto) -> Int = { 0 },
	modifier: Modifier = Modifier
) {
	Column(modifier = modifier) {
		if (config.showHeader) {
			CalendarHeader(
				month = config.currentMonth,
				year = config.currentYear
			)
		}


		AnimatedContent(
			targetState = Pair(config.currentMonth, config.currentYear),
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
				selectedDate = config.selectedDate,
				calendarData = calendarData,
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
			config = CalendarConfigDto(
				currentMonth = 10,
				currentYear = 2025,
				selectedDate = CalendarDateDto(15, 10, 2025, true),
				showHeader = true
			),
			calendarData = calculateCalendarData(10, 2025),
			today = getToday(),
			hasEventsCallback = { date ->
				date.day in listOf(4, 10, 15, 20, 25)
			},
			eventCountCallback = { date ->
				when (date.day) {
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
