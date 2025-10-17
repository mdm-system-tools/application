package org.MdmSystemTools.Application.model.repository

import android.app.Application
import org.MdmSystemTools.Application.model.dto.AssociateDto

class ListAssociateRepositoryImpl(private val appContext: Application) : ListAssociateRepository {
	val listAssociateds = mutableListOf<AssociateDto>()

  init {
		val assoc = AssociateDto(1, 1, "João da Silva Pereira")
		addAssociate(assoc)
	}

  override fun getListAssociates(): List<AssociateDto> {
    return listAssociateds
  }

	override fun getAssociate(id: Int): AssociateDto? {
		val associate = listAssociateds.find { associate ->
			associate.numberCard == id
		}?: return null

		return associate
	}

	override fun addAssociate(associate: AssociateDto) {
		listAssociateds.add(associate)
	}

}