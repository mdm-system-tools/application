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
data class Meeting(@PrimaryKey val id: Int, @ColumnInfo(name = "project_id") var projectId: Int)

@Dao
interface MeetingDao {
  @Insert fun insert(meeting: Meeting): Long

  @Update suspend fun update(vararg meetings: Meeting)

  @Query("SELECT * FROM meeting") fun getAll(): Flow<List<Meeting>>

  @Query("SELECT * FROM meeting WHERE id = :id") suspend fun getByid(id: Int): Meeting

  @Query("DELETE FROM meeting WHERE id = :id") suspend fun delete(id: Int)

  @Delete suspend fun delete(meeting: Meeting)
}
