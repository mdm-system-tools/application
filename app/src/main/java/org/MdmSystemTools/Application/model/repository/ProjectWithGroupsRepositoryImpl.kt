package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.entity.ProjectWithGroups
import org.MdmSystemTools.Application.model.entity.ProjectWithGroupsDao

class ProjectWithGroupsRepositoryImpl(private val dao: ProjectWithGroupsDao) :
	ProjectWithGroupsRepository {
	override suspend fun getProjectWithGroups(projectId: Long): ProjectWithGroups {
		return dao.getProjectWithGroups(projectId)
	}
}