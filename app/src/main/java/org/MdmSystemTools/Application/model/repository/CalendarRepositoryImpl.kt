package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.MdmSystemTools.Application.view.components.Meeting.Calendar.CalendarData
import org.MdmSystemTools.Application.view.components.Meeting.Calendar.CalendarHelper
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
        CalendarHelper.calculateCalendarData(
            _currentMonth.value,
            _currentYear.value
        )
    )
    override val calendarData: StateFlow<CalendarData> = _calendarData.asStateFlow()

    private val _today = MutableStateFlow(CalendarHelper.getToday())
    override val today: StateFlow<Triple<Int, Int, Int>> = _today.asStateFlow()

    override fun navegarProximoMes() {
        val (nextMonth, nextYear) = CalendarHelper.getNextMonth(
            _currentMonth.value,
            _currentYear.value
        )
        navegarParaMes(nextMonth, nextYear)
    }

    override fun navegarMesAnterior() {
        val (prevMonth, prevYear) = CalendarHelper.getPreviousMonth(
            _currentMonth.value,
            _currentYear.value
        )
        navegarParaMes(prevMonth, prevYear)
    }

    override fun navegarParaMes(month: Int, year: Int) {
        _currentMonth.value = month
        _currentYear.value = year
        _calendarData.value = CalendarHelper.calculateCalendarData(month, year)
    }
}
