package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.MdmSystemTools.Application.model.DTO.CalendarData
import org.MdmSystemTools.Application.model.utils.*
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRepositoryImpl @Inject constructor() : CalendarRepository {

    private val _currentMonth = MutableStateFlow(Calendar.getInstance().get(Calendar.MONTH))
    override val currentMonth: StateFlow<Int> = _currentMonth.asStateFlow()

    private val _currentYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
    override val currentYear: StateFlow<Int> = _currentYear.asStateFlow()

    private val _calendarData = MutableStateFlow(
        calculateCalendarData(
            _currentMonth.value,
            _currentYear.value
        )
    )
    override val calendarData: StateFlow<CalendarData> = _calendarData.asStateFlow()

    private val _today = MutableStateFlow(getToday())
    override val today: StateFlow<Triple<Int, Int, Int>> = _today.asStateFlow()

    override fun navigateToNextMonth() {
        val (nextMonth, nextYear) = getNextMonth(
            _currentMonth.value,
            _currentYear.value
        )
        navigateToMonth(nextMonth, nextYear)
    }

    override fun navigateToPreviousMonth() {
        val (prevMonth, prevYear) = getPreviousMonth(
            _currentMonth.value,
            _currentYear.value
        )
        navigateToMonth(prevMonth, prevYear)
    }

    override fun navigateToMonth(month: Int, year: Int) {
        _currentMonth.value = month
        _currentYear.value = year
        _calendarData.value = calculateCalendarData(month, year)
    }
}
