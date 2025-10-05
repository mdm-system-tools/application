package org.MdmSystemTools.Application.model.DTO

data class CalendarConfigDto(
	val currentMonth: Int,
	val currentYear: Int,
	val selectedDate: CalendarDateDto? = null,
	val showHeader: Boolean = true
)
