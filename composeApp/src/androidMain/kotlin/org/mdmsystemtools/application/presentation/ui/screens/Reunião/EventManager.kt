package org.mdmsystemtools.application.presentation.ui.screens.Reunião

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color


data class EventoCompleto(
	val id: String = java.util.UUID.randomUUID().toString(),
	val titulo: String,
	val descricao: String = "",
	val data: CalendarDate,
	val horaInicio: String,
	val horaFim: String,
	val cor: Color,
	val criadoEm: Long = System.currentTimeMillis()
)


class EventManager {
	private val _eventos = mutableStateListOf<EventoCompleto>()
	val eventos: List<EventoCompleto> get() = _eventos.toList()

	fun adicionarEvento(evento: EventoCompleto) {
		_eventos.add(evento)
	}

	fun removerEvento(eventoId: String) {
		_eventos.removeAll { it.id == eventoId }
	}

	fun obterEventosPorData(data: CalendarDate): List<EventoCompleto> {
		return _eventos.filter { evento ->
			evento.data.day == data.day &&
				evento.data.month == data.month &&
				evento.data.year == data.year
		}
	}

	fun temEventosNaData(data: CalendarDate): Boolean {
		return obterEventosPorData(data).isNotEmpty()
	}

	fun obterTodosEventos(): List<EventoCompleto> {
		return _eventos.sortedBy { it.criadoEm }
	}

	companion object {
		// Instância única compartilhada
		private var _instance: EventManager? = null

		@Composable
		fun rememberEventManager(): EventManager {
			if (_instance == null) {
				_instance = EventManager()
			}
			return remember { _instance!! }
		}
	}
}

// Extension functions para facilitar o uso
fun CalendarDate.temEventos(eventManager: EventManager): Boolean {
	return eventManager.temEventosNaData(this)
}

fun CalendarDate.obterEventos(eventManager: EventManager): List<EventoCompleto> {
	return eventManager.obterEventosPorData(this)
}