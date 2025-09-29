package org.MdmSystemTools.Application.model.DTO

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class EventDto(
  val id: String = UUID.randomUUID().toString(),
  val titulo: String,
  val descricao: String = "",
  val data: CalendarDateDto,
  val horaInicio: String,
  val horaFim: String,
  val local: String = "",
  val regiao: String = "",
  val projeto: String = "",
  val grupo: GrupoDto? = null,
  val cor: Color,
  val criadoEm: Long = System.currentTimeMillis()
)
