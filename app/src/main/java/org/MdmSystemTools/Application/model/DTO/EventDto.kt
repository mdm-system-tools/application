package org.MdmSystemTools.Application.model.DTO

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class EventDto(
	val id: String = UUID.randomUUID().toString(),
	val title: String,
	val description: String,
	val date: CalendarDateDto,
	val hourStart: String,
	val hourEnd: String,
	val local: String,
	val region: String,
	val project: String,
	val groups: GroupDto?,
	val color: Color,
	val createdIn: Long? = System.currentTimeMillis()
)
