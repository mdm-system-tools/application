package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.MeetingWithProject
import org.MdmSystemTools.Application.model.entity.MeetingWithProjectDao

class MeetingWithProjectRepositoryImpl(private val dao: MeetingWithProjectDao) :
	MeetingWithProjectRepository {
	override fun getAll(): Flow<List<MeetingWithProject>> {
		return dao.getAll()
	}
}