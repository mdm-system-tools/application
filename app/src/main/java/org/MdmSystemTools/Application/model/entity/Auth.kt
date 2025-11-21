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

@Entity(tableName = "login")
data class Login(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "cpf")
    val cpf: String = "",
    @ColumnInfo(name = "password")
    val password: String = ""
)

@Entity(tableName = "register")
data class Register(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "phone")
    val phone: String = "",
    @ColumnInfo(name = "password")
    val password: String = ""
)

@Dao
interface LoginDao {
    @Query("SELECT * FROM login")
    fun getAll(): Flow<List<Login>>

    @Query("SELECT * FROM login WHERE cpf = :cpf")
    suspend fun getByCpf(cpf: String): Login?

    @Query("SELECT * FROM login WHERE id = :id")
    suspend fun getById(id: Long): Login?

    @Insert
    suspend fun insert(login: Login): Long

    @Update
    suspend fun update(login: Login)

    @Delete
    suspend fun delete(login: Login)

    @Query("DELETE FROM login WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface RegisterDao {
    @Query("SELECT * FROM register")
    fun getAll(): Flow<List<Register>>

    @Query("SELECT * FROM register WHERE email = :email")
    suspend fun getByEmail(email: String): Register?

    @Query("SELECT * FROM register WHERE id = :id")
    suspend fun getById(id: Long): Register?

    @Insert
    suspend fun insert(register: Register): Long

    @Update
    suspend fun update(register: Register)

    @Delete
    suspend fun delete(register: Register)

    @Query("DELETE FROM register WHERE id = :id")
    suspend fun deleteById(id: Long)
}
