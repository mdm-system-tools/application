package org.MdmSystemTools.Application.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class AssociateDto(
	val name: String,
	val numberCard: Int,
	val groupId: Int,
)