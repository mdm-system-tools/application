package org.MdmSystemTools.Application.model.repository

import androidx.compose.ui.graphics.Color
import java.util.Calendar
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.dto.GroupDto

class EventRepositoryImpl : EventRepository {
  val events = mutableListOf<EventDto>()

  init {
    val calendar =
      Calendar.getInstance().apply {
        set(2025, Calendar.OCTOBER, 10, 0, 0, 0)
        set(Calendar.MILLISECOND, 0)
      }

    val timestamp = calendar.timeInMillis
    val event1 =
      EventDto(
        title = "Reunião de Planejamento",
        date = timestamp,
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

  override fun getAllEvents(): List<EventDto> {
    return events
  }
}
