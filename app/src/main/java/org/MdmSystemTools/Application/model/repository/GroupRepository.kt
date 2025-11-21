package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Grupo

interface GroupRepository {
  suspend fun insert(group: Grupo): Long

  fun getAll(): Flow<List<Grupo>>

  suspend fun getById(id:Long): Grupo

  suspend fun delete(id: Long)

  suspend fun delete(group: Grupo)
}
