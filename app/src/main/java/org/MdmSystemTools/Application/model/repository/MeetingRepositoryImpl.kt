package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Meeting
import org.MdmSystemTools.Application.model.entity.MeetingDao

class MeetingRepositoryImpl(private val dao: MeetingDao) : MeetingRepository {
  override suspend fun insert(meeting: Meeting): Long {
    return dao.insert(meeting)
  }

  override suspend fun delete(id: Long) {
    dao.delete(id)
  }

  override suspend fun delete(meeting: Meeting) {
    dao.delete(meeting)
  }

  override suspend fun getAll(): Flow<List<Meeting>> {
    return dao.getAll()
  }

  override suspend fun getById(id: Long): Meeting {
    return dao.getByid(id)
  }
}
