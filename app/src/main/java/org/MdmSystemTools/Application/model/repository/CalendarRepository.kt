package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.StateFlow
import org.MdmSystemTools.Application.view.components.Meeting.Calendar.CalendarData


interface CalendarRepository {

    val currentMonth: StateFlow<Int>

    val currentYear: StateFlow<Int>

    val calendarData: StateFlow<CalendarData>


    val today: StateFlow<Triple<Int, Int, Int>>


    fun navegarProximoMes()


    fun navegarMesAnterior()

    fun navegarParaMes(month: Int, year: Int)
}
