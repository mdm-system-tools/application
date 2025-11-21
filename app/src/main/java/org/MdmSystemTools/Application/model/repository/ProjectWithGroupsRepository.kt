package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.entity.ProjectWithGroups

interface ProjectWithGroupsRepository {
	suspend fun getProjectWithGroups(projectId: Long): ProjectWithGroups
}