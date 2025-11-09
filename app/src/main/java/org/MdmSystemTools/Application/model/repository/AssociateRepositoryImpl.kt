package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.AssociateDto

class AssociateRepositoryImpl() : AssociateRepository {
  val listAssociates = mutableListOf<AssociateDto>()

  init {
    val assoc = AssociateDto(name = "João da Silva Pereira", groupId = 1, numberCard = 100)
    val assoc2 = AssociateDto(name = "Maria Oliveira", groupId = 1, numberCard = 102)
    addAssociate(assoc)
    addAssociate(assoc2)
  }

  override fun getAssociates(): List<AssociateDto> {
    return listAssociates
  }

  override fun getAssociate(id: Int): AssociateDto? {
    val associate = listAssociates.find { associate -> associate.numberCard == id } ?: return null

    return associate
  }

  override fun addAssociate(associate: AssociateDto) {
    listAssociates.add(associate)
  }

  override fun deleteAssociate(id: Int) {
    listAssociates.removeAt(id)
  }
}
