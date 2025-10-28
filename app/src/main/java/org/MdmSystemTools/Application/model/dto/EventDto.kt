package org.MdmSystemTools.Application.model.dto

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class EventDto(
	val id: String = UUID.randomUUID().toString(),
	val title: String = "",
	val description: String = "",
	val date: EventDate = EventDate(1,1,2025),
	val hourStart: String = "",
	val hourEnd: String = "",
	val local: String = "",
	val region: String = "",
	val project: String = "",
	val groups: GroupDto? = null,
	val color: Color = Color.Gray,
	val createdIn: Long = System.currentTimeMillis()
)