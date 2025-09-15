package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.DTO.EventDto

interface EventRepository {
  fun adicionarEvento(evento: EventDto)
  fun removerEvento(eventoId: String)
  fun obterEventosPorData(data: CalendarDateDto): List<EventDto>
  fun temEventosNaData(data: CalendarDateDto): Boolean
  fun obterTodosEventos(): List<EventDto>
}