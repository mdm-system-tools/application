package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.AssociateDto

//TODO Troca para Entity Associate
interface AssociateRepository {
  fun getAll(): List<AssociateDto>

  fun getById(id: Int): AssociateDto?

  fun insert(associate: AssociateDto)

  fun delete(id: Int)

  fun delete(associate: AssociateDto)
}
