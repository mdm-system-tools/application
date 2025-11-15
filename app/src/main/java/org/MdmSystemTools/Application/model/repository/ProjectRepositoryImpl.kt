package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.entity.ProjectDao

class ProjectRepositoryImpl(private val dao: ProjectDao) : ProjectRepository {
  override fun insert(project: Project): Long = dao.insert(project)

  override fun delete(id: Int) {
    // TODO: Implement with coroutine context
    // dao.deleteById(id) - suspend function, needs coroutine
  }

  override fun delete(project: Project) {
    // TODO: Implement with coroutine context
    // dao.delete(project) - suspend function, needs coroutine
  }

  override fun getAll(): Flow<List<Project>> = dao.getAll()

  override fun getById(id: Int): Project {
    // TODO: Implement with coroutine context
    throw UnsupportedOperationException("Use getAll() instead for now")
  }
}
