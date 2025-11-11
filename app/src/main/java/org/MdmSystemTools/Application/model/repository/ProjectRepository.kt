package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.entity.Project

interface ProjectRepository {
	fun addProject(project: Project): Long
}
