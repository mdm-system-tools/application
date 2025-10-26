package org.MdmSystemTools.Application.model.repository

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.MdmSystemTools.Application.model.dto.EventDate
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor() : EventRepository {
	private val _events = MutableStateFlow<List<EventDto>>(emptyList())
	override val events: StateFlow<List<EventDto>> = _events.asStateFlow()

	init {
		val event1 = EventDto(
			title = "Reunião de Planejamento",
			description = "Discussão sobre as metas do próximo trimestre",
			date = EventDate(27, 9, 2025),
			hourStart = "09:00",
			hourEnd = "10:30",
			local = "Sala de Reuniões A",
			region = "Norte",
			project = "Website Corporativo",
			groups = GroupDto("1", "Desenvolvimento", Color(0xFF1C6AEA)),
			color = Color(0xFF1C6AEA)
		)
		addEvent(event1)
	}

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

	override fun getEventsByDate(day: Int, month: Int, year: Int): List<EventDto> {
		return _events.value.filter { event ->
			event.date.day == day &&
				event.date.month == month &&
				event.date.year == year
		}
	}

	override fun hasEventsOnDate(day: Int, month: Int, year: Int): Boolean {
		return getEventsByDate(day, month, year).isNotEmpty()
	}

	override fun getAllEvents(): List<EventDto> {
		return _events.value.sortedBy { it.createdIn }
	}

}