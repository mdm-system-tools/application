package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.ProjectWithGroups

interface ProjectWithGroupsRepository {
	fun getProjectWithGroups(projectId: Long): ProjectWithGroups
	fun getAll(): Flow<List<ProjectWithGroups>>
}