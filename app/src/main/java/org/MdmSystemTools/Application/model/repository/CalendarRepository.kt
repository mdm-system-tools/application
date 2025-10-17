package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.StateFlow
import org.MdmSystemTools.Application.model.dto.CalendarDataDto


interface CalendarRepository {

    val currentMonth: StateFlow<Int>

    val currentYear: StateFlow<Int>

    val calendarDataDto: StateFlow<CalendarDataDto>


    val today: StateFlow<Triple<Int, Int, Int>>


    fun navigateToNextMonth()


    fun navigateToPreviousMonth()

    fun navigateToMonth(month: Int, year: Int)
}
