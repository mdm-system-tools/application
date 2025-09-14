package com.example.myapplication.model

data class CalendarDateDto(
  val day: Int,
  val month: Int,
  val year: Int,
  val isCurrentMonth: Boolean
)
