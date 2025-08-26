package org.mdmsystemtools.application.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AssociatedDao {
	@Insert
	suspend fun insert(associated: AssociatedEntity)

	@Query("SELECT count(*) FROM AssociatedEntity")
	suspend fun count(): Int

	@Query("SELECT * FROM AssociatedEntity")
	fun getAllAssociated(): Flow<List<AssociatedEntity>>
}