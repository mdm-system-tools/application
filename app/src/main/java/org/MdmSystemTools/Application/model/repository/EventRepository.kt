package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.StateFlow
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.DTO.EventDto

interface EventRepository {
	val events: StateFlow<List<EventDto>>

	fun addEvent(event: EventDto)
	fun removeEvent(eventId: String)
	fun getAllEvents(): List<EventDto>
	fun getEventsByDate(date: CalendarDateDto): List<EventDto>
	fun hasEventsOnDate(date: CalendarDateDto): Boolean
}