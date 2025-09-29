package org.MdmSystemTools.Application.model.repository

import android.app.Application
import org.MdmSystemTools.Application.model.DTO.AssociateDto
import org.MdmSystemTools.Application.R

class ListAssociateRepositoryImpl(private val appContext: Application) : ListAssociateRepository {
  init {
    val appName = appContext.getString(R.string.app_name)
    println("Nombre de la aplicación: $appName")
  }

  override fun getListAssociates(): List<AssociateDto> {
    val listAssociateds = mutableListOf<AssociateDto>()
    for (i in 1..100) {
      listAssociateds.add(AssociateDto(i, i, "jose"))
    }
    return listAssociateds
  }

  override fun getAssociate(id: Int): AssociateDto {
    TODO("Not yet implemented")
  }
}