package org.mdmsystemtools.application.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AssociatedDto(
	val numberCard: Int,
	val groupId: Int,
	val name: String,
)