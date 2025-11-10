package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Grupo

interface GroupRepository {
  suspend fun getListGroups(): Flow<List<Grupo>>

  suspend fun addGroup(group: Grupo): Long

	suspend fun deleteGroupById(id: Int)
	suspend fun deleteGroup(group: Grupo)
}
