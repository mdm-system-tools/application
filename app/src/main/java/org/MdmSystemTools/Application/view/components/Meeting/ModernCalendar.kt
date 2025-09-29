package org.MdmSystemTools.Application.view.components.Meeting

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import java.util.*

@Composable
fun ModernCalendar(
    currentMonth: Int,
    currentYear: Int,
    selectedDate: CalendarDateDto? = null,
    onDateClick: (CalendarDateDto) -> Unit = {},
    onMonthChange: (month: Int, year: Int) -> Unit = { _, _ -> },
    hasEventsCallback: (CalendarDateDto) -> Boolean = { false },
    eventCountCallback: (CalendarDateDto) -> Int = { 0 },
    showHeader: Boolean = true,
    modifier: Modifier = Modifier
) {
    // Estado para feedback visual do swipe
    var swipeOffset by remember { mutableStateOf(0f) }
    val animatedOffset by animateFloatAsState(
        targetValue = swipeOffset,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "swipe_offset"
    )

    // Funções de navegação
    val navigateToNextMonth = {
        if (currentMonth == 11) {
            onMonthChange(0, currentYear + 1)
        } else {
            onMonthChange(currentMonth + 1, currentYear)
        }
    }

    val navigateToPreviousMonth = {
        if (currentMonth == 0) {
            onMonthChange(11, currentYear - 1)
        } else {
            onMonthChange(currentMonth - 1, currentYear)
        }
    }

    Column(modifier = modifier) {
        // Header opcional
        if (showHeader) {
            ModernCalendarHeader(
                month = currentMonth,
                year = currentYear
            )
        }

        // Grid do calendário com animação
        AnimatedContent(
            targetState = Pair(currentMonth, currentYear),
            transitionSpec = {
                val targetMonth = targetState.first
                val initialMonth = initialState.first

                val isForward = when {
                    targetMonth > initialMonth || (targetMonth == 0 && initialMonth == 11) -> true
                    else -> false
                }

                slideInHorizontally(
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ) { width -> if (isForward) width else -width } togetherWith
                slideOutHorizontally(
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ) { width -> if (isForward) -width else width }
            },
            label = "calendar_animation"
        ) { (month, year) ->
            ModernCalendarGrid(
                month = month,
                year = year,
                selectedDate = selectedDate,
                onDateClick = onDateClick,
                onSwipeLeft = navigateToNextMonth,
                onSwipeRight = navigateToPreviousMonth,
                hasEventsCallback = hasEventsCallback,
                eventCountCallback = eventCountCallback,
                swipeOffset = swipeOffset,
                onSwipeOffsetChange = { swipeOffset = it }
            )
        }
    }
}

@Composable
private fun ModernCalendarHeader(
    month: Int,
    year: Int
) {
    Column {
        // Título principal - centralizado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${getMonthName(month)} $year",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Indicador sutil de gesto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "◂ Deslize para navegar ▸",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ModernCalendarGrid(
    month: Int,
    year: Int,
    selectedDate: CalendarDateDto? = null,
    onDateClick: (CalendarDateDto) -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    hasEventsCallback: (CalendarDateDto) -> Boolean,
    eventCountCallback: (CalendarDateDto) -> Int,
    swipeOffset: Float,
    onSwipeOffsetChange: (Float) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

    // Data atual
    val currentDate = Calendar.getInstance()
    val actualToday = currentDate.get(Calendar.DAY_OF_MONTH)
    val actualMonth = currentDate.get(Calendar.MONTH)
    val actualYear = currentDate.get(Calendar.YEAR)

    val isShowingCurrentMonth = actualMonth == month && actualYear == year
    val todayDay = if (isShowingCurrentMonth) actualToday else -1

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .offset { IntOffset((swipeOffset * 0.3f).toInt(), 0) }
            .pointerInput(Unit) {
                var totalDragX = 0f
                detectDragGestures(
                    onDragStart = {
                        onSwipeOffsetChange(0f)
                    },
                    onDragEnd = {
                        val threshold = 80f
                        when {
                            totalDragX > threshold -> onSwipeRight()
                            totalDragX < -threshold -> onSwipeLeft()
                        }
                        onSwipeOffsetChange(0f)
                        totalDragX = 0f
                    },
                    onDrag = { _, dragAmount ->
                        totalDragX += dragAmount.x
                        onSwipeOffsetChange(totalDragX.coerceIn(-100f, 100f))
                    }
                )
            }
    ) {
        // Cabeçalho dos dias da semana
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
        }

        // Grid dos dias
        for (week in 0 until 6) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayOfWeek in 0 until 7) {
                    val dayNumber = week * 7 + dayOfWeek - firstDayOfWeek + 1

                    val isSelected = if (dayNumber in 1..daysInMonth && selectedDate != null) {
                        selectedDate.day == dayNumber &&
                        selectedDate.month == month &&
                        selectedDate.year == year
                    } else false

                    ModernCalendarDay(
                        day = dayNumber,
                        isCurrentMonth = dayNumber in 1..daysInMonth,
                        isToday = dayNumber == todayDay,
                        isSelected = isSelected,
                        hasEvents = if (dayNumber in 1..daysInMonth) {
                            val date = CalendarDateDto(dayNumber, month, year, true)
                            hasEventsCallback(date)
                        } else false,
                        eventCount = if (dayNumber in 1..daysInMonth) {
                            val date = CalendarDateDto(dayNumber, month, year, true)
                            eventCountCallback(date)
                        } else 0,
                        onClick = {
                            if (dayNumber in 1..daysInMonth) {
                                val date = CalendarDateDto(dayNumber, month, year, true)
                                onDateClick(date)
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
private fun ModernCalendarDay(
    day: Int,
    isCurrentMonth: Boolean,
    isToday: Boolean,
    isSelected: Boolean = false,
    hasEvents: Boolean,
    eventCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(
                when {
                    isToday && isCurrentMonth -> MaterialTheme.colorScheme.primary
                    isSelected && isCurrentMonth -> MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    hasEvents && isCurrentMonth -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                    else -> Color.Transparent
                }
            )
            .clickable { if (isCurrentMonth) onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isCurrentMonth) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = day.toString(),
                    fontSize = 16.sp,
                    color = when {
                        isToday -> MaterialTheme.colorScheme.onPrimary
                        isSelected -> MaterialTheme.colorScheme.onPrimary
                        hasEvents -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    fontWeight = when {
                        isToday -> FontWeight.Bold
                        isSelected -> FontWeight.Bold
                        hasEvents -> FontWeight.SemiBold
                        else -> FontWeight.Normal
                    }
                )

                // Indicador de eventos
                if (hasEvents && eventCount > 0) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        repeat(minOf(eventCount, 3)) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .background(
                                        when {
                                            isToday || isSelected -> MaterialTheme.colorScheme.onPrimary
                                            else -> MaterialTheme.colorScheme.primary
                                        },
                                        CircleShape
                                    )
                            )
                        }
                        if (eventCount > 3) {
                            Text(
                                text = "+",
                                fontSize = 8.sp,
                                color = when {
                                    isToday || isSelected -> MaterialTheme.colorScheme.onPrimary
                                    else -> MaterialTheme.colorScheme.primary
                                },
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        } else {
            // Dias de outros meses - cinza claro
            Text(
                text = if (day > 0) day.toString() else "",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                fontWeight = FontWeight.Light
            )
        }
    }
}

private fun getMonthName(month: Int): String {
    return listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )[month]
}