package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.ProjectWithGroups
import org.MdmSystemTools.Application.model.entity.ProjectWithGroupsDao

class ProjectWithGroupsRepositoryImpl(private val dao: ProjectWithGroupsDao) :
	ProjectWithGroupsRepository {
	override fun getProjectWithGroups(projectId: Long): ProjectWithGroups {
		return dao.getProjectWithGroups(projectId)
	}

	override fun getAll(): Flow<List<ProjectWithGroups>> {
		return dao.getAll()
	}
}