package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.DTO.AssociatedDto

interface ListAssociatedRepository {
  fun getListAssociateds(): List<AssociatedDto>
  fun getAssociated(id: Int): AssociatedDto
}