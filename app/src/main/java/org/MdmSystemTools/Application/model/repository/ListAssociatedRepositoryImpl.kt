package org.MdmSystemTools.Application.model.repository

import android.app.Application
import org.MdmSystemTools.Application.model.DTO.AssociatedDto
import com.MdmSystemTools.Application.R

class ListAssociatedRepositoryImpl(private val appContext: Application) : ListAssociatedRepository {
  init {
    val appName = appContext.getString(R.string.app_name)
    println("Nombre de la aplicación: $appName")
  }

  override fun getListAssociateds(): List<AssociatedDto> {
    val listAssociateds = mutableListOf<AssociatedDto>()
    for (i in 1..100) {
      listAssociateds.add(AssociatedDto(i, i, "jose"))
    }
    return listAssociateds
  }

  override fun getAssociated(id: Int): AssociatedDto {
    TODO("Not yet implemented")
  }
}