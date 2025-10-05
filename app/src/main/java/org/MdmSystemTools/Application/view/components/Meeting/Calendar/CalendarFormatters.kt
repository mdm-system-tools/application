package org.MdmSystemTools.Application.view.components.Meeting.Calendar

import org.MdmSystemTools.Application.view.theme.AppConstants

internal fun getMonthName(month: Int): String {
	return AppConstants.Calendar.monthNames[month]
}

internal fun getWeekDayAbbreviations(): List<String> {
	return AppConstants.Calendar.weekDayAbbreviations
}
