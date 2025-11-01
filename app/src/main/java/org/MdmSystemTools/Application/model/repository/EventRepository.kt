package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.EventDto

interface EventRepository {
  fun addEvent(event: EventDto)

  fun removeEvent(id: Int)

  fun getAllEvents(): List<EventDto>

  fun getEventsByDate(day: Int, month: Int, year: Int): List<EventDto>
}
