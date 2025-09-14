package com.example.myapplication.model

import androidx.compose.ui.graphics.Color

data class EventDto(
  val id: String = java.util.UUID.randomUUID().toString(),
  val titulo: String,
  val descricao: String = "",
  val data: CalendarDateDto,
  val horaInicio: String,
  val horaFim: String,
  val cor: Color,
  val criadoEm: Long = System.currentTimeMillis()
)
