package org.MdmSystemTools.Application.model.entity

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(
	foreignKeys = [
		ForeignKey(
			entity = Project::class,
			parentColumns = ["id"],
			childColumns = ["project_id"],
			onDelete = ForeignKey.CASCADE,
		)
	], indices = [Index("project_id")]
)
data class Grupo(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	@ColumnInfo(name = "project_id") val projectId: Long,
	@ColumnInfo(name = "schedule") var schedule: String,
)

@Dao
interface GrupoDao {
	@Query("SELECT * FROM grupo")
	fun getAll(): Flow<List<Grupo>>

	@Delete
	suspend fun delete(group: Grupo)

	@Insert
	suspend fun insert(group: Grupo): Long

	@Query("SELECT * FROM grupo WHERE grupo.id = :id")
	suspend fun getByid(id: Long): Grupo

	@Update
	suspend fun updateGroup(vararg groups: Grupo)

	@Query("DELETE FROM grupo WHERE id = :id")
	suspend fun deleteById(id: Long)
}
