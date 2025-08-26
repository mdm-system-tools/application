package org.mdmsystemtools.application.domain.repository

import org.mdmsystemtools.application.data.model.AssociatedDto

interface AssociatedRepository {
	suspend fun getAllAssociated(): List<AssociatedDto>
	suspend fun postAssociated(associated: AssociatedDto): Boolean
}