package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Associate

interface AssociateRepository {
	suspend fun insert(associate: Associate): Long
	suspend fun getAll(): Flow<List<Associate>>
	suspend fun getById(numberCard: String): Associate
	suspend fun delete(numberCard: String)
	suspend fun delete(associate: Associate)
}
