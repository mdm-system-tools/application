package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.DTO.EventDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor() : EventRepository {
	private val _events = MutableStateFlow<List<EventDto>>(emptyList())
	override val events: StateFlow<List<EventDto>> = _events.asStateFlow()

	override fun addEvent(event: EventDto) {
		val currentList = _events.value.toMutableList()
		currentList.add(event)
		_events.value = currentList
	}

	override fun removeEvent(eventId: String) {
		val currentList = _events.value.toMutableList()
		currentList.removeAll { it.id == eventId }
		_events.value = currentList
	}

	override fun getEventsByDate(date: CalendarDateDto): List<EventDto> {
		return _events.value.filter { event ->
			event.date.day == date.day &&
				event.date.month == date.month &&
				event.date.year == date.year
		}
	}

	override fun hasEventsOnDate(date: CalendarDateDto): Boolean {
		return getEventsByDate(date).isNotEmpty()
	}

	override fun getAllEvents(): List<EventDto> {
		return _events.value.sortedBy { it.createdIn }
	}

}

// Extension functions to facilitate usage
fun CalendarDateDto.hasEvents(eventRepositoryImpl: EventRepositoryImpl): Boolean {
	return eventRepositoryImpl.hasEventsOnDate(this)
}

fun CalendarDateDto.getEvents(eventRepositoryImpl: EventRepositoryImpl): List<EventDto> {
	return eventRepositoryImpl.getEventsByDate(this)
}