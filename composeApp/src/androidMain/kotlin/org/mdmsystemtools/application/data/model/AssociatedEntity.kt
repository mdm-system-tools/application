package org.mdmsystemtools.application.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AssociatedEntity(
	@PrimaryKey(autoGenerate = true)
	val numberCard: Int,
	val groupId: Int,
	val name: String
)
