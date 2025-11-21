package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.MdmSystemTools.Application.model.entity.Associate
import org.MdmSystemTools.Application.model.entity.AssociateDao

class AssociateRepositoryImpl(private val dao: AssociateDao) : AssociateRepository {
	override suspend fun insert(associate: Associate): Long {
		return dao.insert(associate)
	}

	override suspend fun getAll(): Flow<List<Associate>> {
		return dao.getAll().map { list ->
			list.map {
				Associate(it.numberCard, it.name, it.groupId)
			}
		}
	}

	override suspend fun getById(numberCard: String): Associate {
		return dao.getByid(numberCard)
	}

	override suspend fun delete(numberCard: String) {
		dao.delete(numberCard)
	}


	override suspend fun delete(associate: Associate) {
		dao.delete(associate)
	}

	override suspend fun count(): Flow<Int> {
		return dao.count()
	}
}
