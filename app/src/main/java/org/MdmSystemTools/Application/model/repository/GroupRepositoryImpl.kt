package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.entity.GrupoDao

class GroupRepositoryImpl(private val dao: GrupoDao) : GroupRepository {

  override suspend fun getListGroups(): Flow<List<Grupo>> {
    return dao.getAll().map { list -> list.map { Grupo(it.id, it.projectId, it.schedule) } }
  }

  override suspend fun addGroup(group: Grupo): Long {
    return dao.insert(group)
  }

  override suspend fun deleteGroupById(id: Int) {
    dao.deleteById(id)
  }

  override suspend fun deleteGroup(group: Grupo) {
    dao.delete(group)
  }
}
