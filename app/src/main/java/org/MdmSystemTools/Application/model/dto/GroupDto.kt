package org.MdmSystemTools.Application.model.dto

import androidx.compose.ui.graphics.Color

data class GroupDto(
	val id: String,
	val nome: String,
	val cor: Color,
	val descricao: String = ""
)