package org.mdmsystemtools.application.presentation.ui.screens.Reunião

import androidx.compose.runtime.*
import com.example.myapplication.model.CalendarDateDto
import com.example.myapplication.model.EventDto
import com.example.myapplication.repository.EventRepository

class EventRepositoryImpl : EventRepository {
	private val _eventos = mutableStateListOf<EventDto>()
	val eventos: List<EventDto> get() = _eventos.toList()

	override fun adicionarEvento(evento: EventDto) {
		_eventos.add(evento)
	}

	override fun removerEvento(eventoId: String) {
		_eventos.removeAll { it.id == eventoId }
	}

	override fun obterEventosPorData(data: CalendarDateDto): List<EventDto> {
		return _eventos.filter { evento ->
			evento.data.day == data.day &&
				evento.data.month == data.month &&
				evento.data.year == data.year
		}
	}

	override fun temEventosNaData(data: CalendarDateDto): Boolean {
		return obterEventosPorData(data).isNotEmpty()
	}

	override fun obterTodosEventos(): List<EventDto> {
		return _eventos.sortedBy { it.criadoEm }
	}

	companion object {
		// Instância única compartilhada
		private var _instance: EventRepositoryImpl? = null

		@Composable
		fun rememberEventManager(): EventRepositoryImpl {
			if (_instance == null) {
				_instance = EventRepositoryImpl()
			}
			return remember { _instance!! }
		}
	}
}

// Extension functions para facilitar o uso
fun CalendarDateDto.temEventos(eventRepositoryImpl: EventRepositoryImpl): Boolean {
	return eventRepositoryImpl.temEventosNaData(this)
}

fun CalendarDateDto.obterEventos(eventRepositoryImpl: EventRepositoryImpl): List<EventDto> {
	return eventRepositoryImpl.obterEventosPorData(this)
}