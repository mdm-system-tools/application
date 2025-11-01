package org.MdmSystemTools.Application.model.repository

import androidx.compose.ui.graphics.Color
import org.MdmSystemTools.Application.model.dto.EventDate
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.dto.GroupDto

class EventRepositoryImpl : EventRepository {
  val events = mutableListOf<EventDto>()

  init {
    val event1 =
      EventDto(
        title = "Reunião de Planejamento",
        description = "Discussão sobre as metas do próximo trimestre",
        date = EventDate(27, 9, 2025),
        hourStart = "09:00",
        hourEnd = "10:30",
        local = "Sala de Reuniões A",
        region = "Norte",
        project = "Website Corporativo",
        groups = GroupDto("1", "Desenvolvimento", Color(0xFF1C6AEA)),
        color = Color(0xFF1C6AEA),
      )
    addEvent(event1)
  }

  override fun addEvent(event: EventDto) {
    events.add(event)
  }

  override fun removeEvent(id: Int) {
    events.removeAt(id)
  }

  override fun getEventsByDate(day: Int, month: Int, year: Int): List<EventDto> {
    return events.filter { event ->
      event.date.day == day && event.date.month == month && event.date.year == year
    }
  }

  override fun getAllEvents(): List<EventDto> {
    return events
  }
}
