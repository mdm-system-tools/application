package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.entity.ProjectDao

class ProjectRepositoryImpl(private val dao : ProjectDao): ProjectRepository {
	override fun addProject(project: Project): Long = dao.insert(project)
}
