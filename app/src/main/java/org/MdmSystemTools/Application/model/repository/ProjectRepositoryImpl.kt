package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.entity.ProjectDao

class ProjectRepositoryImpl(private val dao: ProjectDao) : ProjectRepository {
  override fun insert(project: Project): Long = dao.insert(project)

  override suspend fun delete(id: Long) {
		dao.delete(id)
  }

  override suspend fun delete(project: Project) {
		dao.delete(project)
  }

  override fun getAll(): Flow<List<Project>> = dao.getAll()

  override suspend fun getById(id: Long): Project {
		return dao.getByid(id)
  }

	override suspend fun count(): Flow<Int> {
		return dao.count()
	}

	override suspend fun countCompleted(): Flow<Int> {
		return dao.countCompleted()
	}
}
