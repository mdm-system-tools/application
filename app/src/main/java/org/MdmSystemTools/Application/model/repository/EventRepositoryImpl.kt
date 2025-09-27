package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.DTO.EventDto
import org.MdmSystemTools.Application.model.repository.EventRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor() : EventRepository {
	private val _eventos = MutableStateFlow<List<EventDto>>(emptyList())
	val eventos: StateFlow<List<EventDto>> = _eventos.asStateFlow()

	override fun adicionarEvento(evento: EventDto) {
		val currentList = _eventos.value.toMutableList()
		currentList.add(evento)
		_eventos.value = currentList
	}

	override fun removerEvento(eventoId: String) {
		val currentList = _eventos.value.toMutableList()
		currentList.removeAll { it.id == eventoId }
		_eventos.value = currentList
	}

	override fun obterEventosPorData(data: CalendarDateDto): List<EventDto> {
		return _eventos.value.filter { evento ->
			evento.data.day == data.day &&
				evento.data.month == data.month &&
				evento.data.year == data.year
		}
	}

	override fun temEventosNaData(data: CalendarDateDto): Boolean {
		return obterEventosPorData(data).isNotEmpty()
	}

	override fun obterTodosEventos(): List<EventDto> {
		return _eventos.value.sortedBy { it.criadoEm }
	}

}

// Extension functions para facilitar o uso
fun CalendarDateDto.temEventos(eventRepositoryImpl: EventRepositoryImpl): Boolean {
	return eventRepositoryImpl.temEventosNaData(this)
}

fun CalendarDateDto.obterEventos(eventRepositoryImpl: EventRepositoryImpl): List<EventDto> {
	return eventRepositoryImpl.obterEventosPorData(this)
}