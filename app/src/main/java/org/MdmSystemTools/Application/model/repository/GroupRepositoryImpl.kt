package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.entity.GrupoDao

class GroupRepositoryImpl(private val dao: GrupoDao) : GroupRepository {

  override suspend fun getAll(): Flow<List<Grupo>> {
    return dao.getAll().map { list -> list.map { Grupo(it.id, it.projectId, it.schedule) } }
  }

	override suspend fun getById(): Grupo {
		TODO("Not yet implemented")
	}

	override suspend fun insert(group: Grupo): Long {
    return dao.insert(group)
  }

  override suspend fun delete(id: Int) {
    dao.deleteById(id)
  }

  override suspend fun delete(group: Grupo) {
    dao.delete(group)
  }
}
