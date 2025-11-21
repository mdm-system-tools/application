package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.entity.GrupoDao

class GroupRepositoryImpl(private val dao: GrupoDao) : GroupRepository {

	override fun getAll(): Flow<List<Grupo>> {
		return dao.getAll().map { list -> list.map { Grupo(it.id, it.projectId, it.schedule) } }
	}

	override suspend fun getById(id: Long): Grupo {
		return dao.getByid(id)
	}

	override suspend fun insert(group: Grupo): Long {
		return dao.insert(group)
	}

	override suspend fun delete(id: Long) {
		dao.deleteById(id)
	}

	override suspend fun delete(group: Grupo) {
		dao.delete(group)
	}
}
