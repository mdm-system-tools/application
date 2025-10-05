package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.StateFlow
import org.MdmSystemTools.Application.model.DTO.EventDto

interface EventRepository {
	val events: StateFlow<List<EventDto>>

	fun addEvent(event: EventDto)
	fun removeEvent(eventId: String)
	fun getAllEvents(): List<EventDto>
	fun getEventsByDate(day: Int, month: Int, year: Int): List<EventDto>
	fun hasEventsOnDate(day: Int, month: Int, year: Int): Boolean
}