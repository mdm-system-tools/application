package org.MdmSystemTools.Application.model.entity

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction

data class ProjectWithGroups(
	@Embedded val project: Project,
	@Relation(parentColumn = "id", entityColumn = "project_id") val groups: List<Grupo>,
)

@Dao
interface ProjectWithGroupsDao {
	@Transaction
	@Query("SELECT * FROM Project WHERE id = :projectId")
	suspend fun getProjectWithGroups(projectId: Long): ProjectWithGroups
}
