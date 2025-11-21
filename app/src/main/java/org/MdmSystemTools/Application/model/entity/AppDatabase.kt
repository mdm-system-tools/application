package org.MdmSystemTools.Application.model.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [Associate::class, Grupo::class, Project::class, Meeting::class, Login::class, Register::class],
  version = 1,
  exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun associateDao(): AssociateDao
	abstract fun groupDao(): GrupoDao
  abstract fun projectDao(): ProjectDao
	abstract fun meetingDao(): MeetingDao
	abstract fun projectWithGroups(): ProjectWithGroupsDao
	abstract fun loginDao(): LoginDao
	abstract fun registerDao(): RegisterDao
}
