package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.MeetingWithProject

interface MeetingWithProjectRepository {
	fun getAll(): Flow<List<MeetingWithProject>>
}