package org.MdmSystemTools.Application.model.repository

import android.app.Application
import org.MdmSystemTools.Application.model.DTO.AssociateDto
import org.MdmSystemTools.Application.R

class ListAssociateRepositoryImpl(private val appContext: Application) : ListAssociateRepository {
	val listAssociateds = mutableListOf<AssociateDto>()

  init { }

  override fun getListAssociates(): List<AssociateDto> {
    return listAssociateds
  }

	override fun getAssociate(id: Int): AssociateDto? {
		val associate = listAssociateds.find { associate ->
			associate.numberCard == id
		}?: return null

		return associate
	}

	override fun createAssociate(associate: AssociateDto) {
		listAssociateds.add(associate)
	}

}