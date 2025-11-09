package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.AssociateDto

interface AssociateRepository {
	fun getAssociates(): List<AssociateDto>
	fun getAssociate(id: Int): AssociateDto?
	fun addAssociate(associate: AssociateDto)
	fun deleteAssociate(id: Int)
}