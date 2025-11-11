package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.entity.ProjectDao

class ProjectRepositoryImpl(private val dao: ProjectDao) : ProjectRepository {
  override fun insert(project: Project): Long = dao.insert(project)

  override fun delete(id: Int) {
    TODO("Not yet implemented")
  }

  override fun delete(project: Project) {
    TODO("Not yet implemented")
  }

  override fun getAll(): Flow<List<Project>> {
    TODO("Not yet implemented")
  }

  override fun getById(id: Int): Project {
    TODO("Not yet implemented")
  }
}
