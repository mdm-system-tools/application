package org.MdmSystemTools.Application.model.entity

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


data class MeetingWithProject(
	@Embedded val meeting: Meeting,
//	@Relation(parentColumn = "id", entityColumn = "meeting_id") val project: Project
	val project: Project
)

@Dao
interface MeetingWithProjectDao {
	@Transaction
	@Query("SELECT * FROM meeting")
	fun getAll(): Flow<List<MeetingWithProject>>
}
