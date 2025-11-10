package org.MdmSystemTools.Application.model.entity

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity
data class Associate(
  @PrimaryKey @ColumnInfo(name = "number_card") var numberCard: String,
  @ColumnInfo(name = "name") var name: String?,
  @ColumnInfo(name = "group_id") var groupId: Int?,
)

@Dao
interface AssociateDao {
  @Query("SELECT * FROM associate") suspend fun getAll(): List<Associate>

  @Delete suspend fun delete(associate: Associate)

  @Insert suspend fun insert(associate: Associate)

  @Query("SELECT * FROM associate " + "WHERE associate.number_card = :numberCard")
  suspend fun getByid(numberCard: String): Associate

  @Update suspend fun updateAssociate(vararg associates: Associate)
}
