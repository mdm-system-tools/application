package org.MdmSystemTools.Application.model.repository

import kotlinx.coroutines.flow.Flow
import org.MdmSystemTools.Application.model.entity.Project

interface ProjectRepository {
  fun insert(project: Project): Long

  fun delete(id: Int)

  fun delete(project: Project)

  fun getAll(): Flow<List<Project>>

  fun getById(id: Int): Project
}
