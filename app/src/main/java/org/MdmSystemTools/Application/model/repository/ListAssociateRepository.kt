package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.DTO.AssociateDto

interface ListAssociateRepository {
  fun getListAssociates(): List<AssociateDto>
  fun getAssociate(id: Int): AssociateDto
}