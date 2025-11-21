package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Meeting

interface MeetingRepository {
  suspend fun insert(meeting: Meeting): Long

  suspend fun delete(id: Long)

  suspend fun delete(meeting: Meeting)

  suspend fun getAll(): Flow<List<Meeting>>

  suspend fun getById(id: Long): Meeting
}
