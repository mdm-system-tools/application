package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.AssociateDto

class AssociateRepositoryImpl() : AssociateRepository {
  val listAssociates = mutableListOf<AssociateDto>()

  init {
    val assoc = AssociateDto(name = "João da Silva Pereira", groupId = 1, numberCard = 100)
    val assoc2 = AssociateDto(name = "Maria Oliveira", groupId = 1, numberCard = 102)
    insert(assoc)
    insert(assoc2)
  }

  override fun getAll(): List<AssociateDto> {
    return listAssociates
  }

  override fun getById(id: Int): AssociateDto? {
    val associate = listAssociates.find { associate -> associate.numberCard == id } ?: return null

    return associate
  }

  override fun insert(associate: AssociateDto) {
    listAssociates.add(associate)
  }

  override fun delete(id: Int) {
    listAssociates.removeAt(id)
  }

	override fun delete(associate: AssociateDto) {
		TODO("Not yet implemented")
	}
}
