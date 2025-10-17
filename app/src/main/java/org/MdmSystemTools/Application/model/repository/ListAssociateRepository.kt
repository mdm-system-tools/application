package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.AssociateDto

interface ListAssociateRepository {
  fun getListAssociates(): List<AssociateDto>
  fun getAssociate(id: Int): AssociateDto?
	fun createAssociate(associate: AssociateDto)
}