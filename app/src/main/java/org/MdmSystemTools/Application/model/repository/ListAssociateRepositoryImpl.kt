package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.AssociateDto

class ListAssociateRepositoryImpl() : ListAssociateRepository {
	val listAssociates = mutableListOf<AssociateDto>()

	init {
		val assoc = AssociateDto(
			name = "João da Silva Pereira",
			groupId = 1,
			numberCard = 1,
		)
		addAssociate(assoc)
		addAssociate(assoc)
	}

	override fun getListAssociates(): List<AssociateDto> {
		return listAssociates
	}

	override fun getAssociate(id: Int): AssociateDto? {
		val associate = listAssociates.find { associate ->
			associate.numberCard == id
		} ?: return null

		return associate
	}

	override fun addAssociate(associate: AssociateDto) {
		listAssociates.add(associate)
	}

	override fun deleteAssociate(id: Int) {
		listAssociates.removeAt(id)
	}

}