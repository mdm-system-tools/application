package org.MdmSystemTools.Application.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class AssociateDto(
	val name: String = "",
	val numberCard: Int = 0,
	val groupId: Int = 0,
	val isValid: Boolean = false,
	val errorMessage: String? = null,
)