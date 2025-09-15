package org.MdmSystemTools.Application.model.DTO

data class CalendarDateDto(
  val day: Int,
  val month: Int,
  val year: Int,
  val isCurrentMonth: Boolean
)
