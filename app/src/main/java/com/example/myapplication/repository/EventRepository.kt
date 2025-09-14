package com.example.myapplication.repository

import com.example.myapplication.model.CalendarDateDto
import com.example.myapplication.model.EventDto

interface EventRepository {
  fun adicionarEvento(evento: EventDto)
  fun removerEvento(eventoId: String)
  fun obterEventosPorData(data: CalendarDateDto): List<EventDto>
  fun temEventosNaData(data: CalendarDateDto): Boolean
  fun obterTodosEventos(): List<EventDto>
}