package org.MdmSystemTools.Application.view.components.Meeting.Calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.model.DTO.CalendarData
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.utils.getNextMonth
import org.MdmSystemTools.Application.model.utils.getPreviousMonth
import org.MdmSystemTools.Application.view.theme.AppConstants
import java.util.Calendar
import kotlin.math.abs


@Composable
internal fun CalendarGrid(
	month: Int,
	year: Int,
	selectedDate: CalendarDateDto?,
	calendarData: CalendarData,
	today: Triple<Int, Int, Int>,
	onDateClick: (CalendarDateDto) -> Unit,
	onMonthChange: (Int, Int) -> Unit,
	hasEventsCallback: (CalendarDateDto) -> Boolean,
	eventCountCallback: (CalendarDateDto) -> Int,
	modifier: Modifier = Modifier
) {

	var swipeOffsetX by remember { mutableFloatStateOf(0f) }

	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = AppConstants.Spacing.medium)
	) {
		WeekDaysHeader()

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.offset(x = (swipeOffsetX / 3).dp)
				.alpha(1f - (abs(swipeOffsetX) / 800f))
				.pointerInput(Unit) {
					detectHorizontalDragGestures(
						onDragEnd = {
							if (swipeOffsetX > AppConstants.Animation.swipeOffsetMax) {
								// Swipe direita - mês anterior
								val (prevMonth, prevYear) = getPreviousMonth(month, year)
								onMonthChange(prevMonth, prevYear)
							} else if (swipeOffsetX < AppConstants.Animation.swipeOffsetMin) {
								// Swipe esquerda - próximo mês
								val (nextMonth, nextYear) = getNextMonth(month, year)
								onMonthChange(nextMonth, nextYear)
							}
							swipeOffsetX = 0f
						},
						onHorizontalDrag = { _, dragAmount ->
							swipeOffsetX += dragAmount
						}
					)
				}
		) {
			DaysGrid(
				calendarData = calendarData,
				month = month,
				year = year,
				today = today,
				selectedDate = selectedDate,
				onDateClick = onDateClick,
				hasEventsCallback = hasEventsCallback,
				eventCountCallback = eventCountCallback
			)
		}
	}
}

@Composable
internal fun WeekDaysHeader(modifier: Modifier = Modifier) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = AppConstants.Spacing.small)
	) {
		getWeekDayAbbreviations().forEach { day ->
			Text(
				text = day,
				modifier = Modifier.weight(1f),
				textAlign = TextAlign.Center,
				fontSize = AppConstants.FontSize.medium,
				fontWeight = FontWeight.Medium,
				color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = AppConstants.Alpha.semiTransparent)
			)
		}
	}
}


@Composable
internal fun DaysGrid(
	calendarData: CalendarData,
	month: Int,
	year: Int,
	today: Triple<Int, Int, Int>,
	selectedDate: CalendarDateDto?,
	onDateClick: (CalendarDateDto) -> Unit,
	hasEventsCallback: (CalendarDateDto) -> Boolean,
	eventCountCallback: (CalendarDateDto) -> Int,
	modifier: Modifier = Modifier
) {
	val totalCells = 42 // 6 semanas x 7 dias
	val days = (0 until totalCells).map { index ->
		index - calendarData.firstDayOfWeek + 1
	}

	Column(modifier = modifier) {
		for (week in 0 until 6) {
			Row(modifier = Modifier.fillMaxWidth()) {
				for (dayOfWeek in 0 until 7) {
					val cellIndex = week * 7 + dayOfWeek
					val dayNumber = days[cellIndex]
					val isValidDay = dayNumber in 1..calendarData.daysInMonth

					CalendarDay(
						day = dayNumber,
						isValidDay = isValidDay,
						isToday = isValidDay && dayNumber == today.first &&
								month == today.second && year == today.third,
						isSelected = isValidDay && selectedDate?.let {
							it.day == dayNumber && it.month == month && it.year == year
						} ?: false,
						hasEvents = if (isValidDay) {
							hasEventsCallback(CalendarDateDto(dayNumber, month, year, true))
						} else false,
						eventCount = if (isValidDay) {
							eventCountCallback(CalendarDateDto(dayNumber, month, year, true))
						} else 0,
						onClick = {
							if (isValidDay) {
								onDateClick(CalendarDateDto(dayNumber, month, year, true))
							}
						},
						modifier = Modifier.weight(1f)
					)
				}
			}
		}
	}
}

@Composable
internal fun CalendarDay(
	day: Int,
	isValidDay: Boolean,
	isToday: Boolean,
	isSelected: Boolean,
	hasEvents: Boolean,
	eventCount: Int,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {

	val backgroundColor = when {
		isSelected && isValidDay -> MaterialTheme.colorScheme.primary.copy(alpha = AppConstants.Alpha.strong)
		isToday && isValidDay && !hasEvents -> MaterialTheme.colorScheme.primary
		hasEvents && isValidDay -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = AppConstants.Alpha.subtle)
		else -> Color.Transparent
	}

	val textColor = when {
		!isValidDay -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = AppConstants.Alpha.disabled)
		isSelected || (isToday && !hasEvents) -> MaterialTheme.colorScheme.onPrimary
		hasEvents -> MaterialTheme.colorScheme.primary
		else -> MaterialTheme.colorScheme.onSurface
	}

	val fontWeight = when {
		isSelected || (isToday && !hasEvents) -> FontWeight.Bold
		hasEvents -> FontWeight.SemiBold
		else -> FontWeight.Normal
	}

	Box(
		modifier = modifier
			.aspectRatio(1f)
			.padding(AppConstants.Spacing.tiny)
			.clip(CircleShape)
			.background(backgroundColor)
			.clickable(enabled = isValidDay) { onClick() },
		contentAlignment = Alignment.Center
	) {
		if (isValidDay) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			) {
				Text(
					text = day.toString(),
					fontSize = AppConstants.FontSize.large,
					color = textColor,
					fontWeight = fontWeight
				)

				if (hasEvents && eventCount > 0) {
					Spacer(modifier = Modifier.height(AppConstants.Spacing.tiny))
					EventIndicators(
						eventCount = eventCount,
						isSelected = isSelected
					)
				}
			}
		} else {
			Text(
				text = if (day > 0) day.toString() else "",
				fontSize = AppConstants.FontSize.large,
				color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = AppConstants.Alpha.disabled),
				fontWeight = FontWeight.Light
			)
		}
	}
}
