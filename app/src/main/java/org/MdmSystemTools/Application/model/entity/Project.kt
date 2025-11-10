package org.MdmSystemTools.Application.model.entity

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity
data class Project(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "regiao") var region: String,
  @ColumnInfo(name = "nome") var name: String,
  @ColumnInfo(name = "valor") var value: Int,
)
@Dao
interface ProjectDao {
	@Query("SELECT * FROM project") fun getAll(): Flow<List<Project>>

	@Delete suspend fun delete(group: Project)

	@Insert suspend fun insert(group: Project) : Long

	@Query("SELECT * FROM project WHERE id = :id") suspend fun getByid(id: Int): Project

	@Update suspend fun updateGroup(vararg projects: Project)

	@Query("DELETE FROM grupo WHERE id = :id") suspend fun deleteById(id: Int)
}