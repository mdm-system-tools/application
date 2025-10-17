package org.MdmSystemTools.Application.model.dto

import androidx.compose.ui.graphics.Color

data class GroupDto(
	val id: String,
	val name: String,
	val color: Color,
	val description: String = ""
)