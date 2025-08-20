package org.mdmsystemtools.application.Telas.Api

import kotlinx.serialization.Serializable

@Serializable
data class Associated(
	val numberCard: Int,
	val groupId: Int,
	val name: String
)