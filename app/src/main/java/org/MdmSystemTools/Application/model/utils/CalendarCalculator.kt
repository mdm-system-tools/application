package org.MdmSystemTools.Application.model.utils

import org.MdmSystemTools.Application.model.DTO.CalendarData
import java.util.Calendar

fun calculateCalendarData(month: Int, year: Int): CalendarData {
	val calendar = Calendar.getInstance().apply {
		set(year, month, 1)
	}
	return CalendarData(
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
		firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
	)
}

fun getToday(): Triple<Int, Int, Int> {
	val now = Calendar.getInstance()
	return Triple(
		now.get(Calendar.DAY_OF_MONTH),
		now.get(Calendar.MONTH),
		now.get(Calendar.YEAR)
	)
}

fun getPreviousMonth(currentMonth: Int, currentYear: Int): Pair<Int, Int> {
	return if (currentMonth == 0) {
		Pair(11, currentYear - 1)
	} else {
		Pair(currentMonth - 1, currentYear)
	}
}

fun getNextMonth(currentMonth: Int, currentYear: Int): Pair<Int, Int> {
	return if (currentMonth == 11) {
		Pair(0, currentYear + 1)
	} else {
		Pair(currentMonth + 1, currentYear)
	}
}
