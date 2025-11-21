package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Project

interface ProjectRepository {
  fun insert(project: Project): Long

  suspend fun delete(id: Int)

  suspend fun delete(project: Project)

  fun getAll(): Flow<List<Project>>

  suspend fun getById(id: Int): Project
}
