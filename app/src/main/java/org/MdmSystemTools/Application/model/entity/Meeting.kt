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
import androidx.room.TypeConverter
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

enum class TypeMeetingLocal { INTERNO, EXTERNO }

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
data class Meeting(
	@PrimaryKey val id: Long = 0,
	@ColumnInfo(name = "project_id") var projectId: Long,
	@ColumnInfo(name = "date", defaultValue = "CURRENT_TIMESTAMP") var date: Long,
	@ColumnInfo(name = "address") var address: String,
	@ColumnInfo(name = "local") var local: TypeMeetingLocal = TypeMeetingLocal.INTERNO,
)

class Converters {
	@TypeConverter
	fun fromLocal(t: TypeMeetingLocal) = t.name

	@TypeConverter
	fun toLocal(s: String) = TypeMeetingLocal.valueOf(s)
}

@Dao
interface MeetingDao {
	@Insert
	fun insert(meeting: Meeting): Long

	@Update
	suspend fun update(vararg meetings: Meeting)

	@Query("SELECT * FROM meeting")
	fun getAll(): Flow<List<Meeting>>

	@Query("SELECT * FROM meeting WHERE id = :id")
	suspend fun getByid(id: Long): Meeting

	@Query("DELETE FROM meeting WHERE id = :id")
	suspend fun delete(id: Long)

	@Delete
	suspend fun delete(meeting: Meeting)
}
