package org.MdmSystemTools.Application

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.MdmSystemTools.Application.model.entity.AppDatabase
import org.MdmSystemTools.Application.model.entity.GrupoDao
import org.MdmSystemTools.Application.model.entity.ProjectDao
import org.MdmSystemTools.Application.model.entity.ProjectWithGroupsDao
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.AssociateRepositoryImpl
import org.MdmSystemTools.Application.model.repository.EventRepository
import org.MdmSystemTools.Application.model.repository.EventRepositoryImpl
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.model.repository.GroupRepositoryImpl
import org.MdmSystemTools.Application.model.repository.ProjectRepository
import org.MdmSystemTools.Application.model.repository.ProjectRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
    // por enquanto o banco será em memoria
		// por enquanto está permitido acessa o banco de dados na main thread
    return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()

    // return Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
  }

  @Provides fun provideGroupDao(db: AppDatabase): GrupoDao = db.groupDao()

  @Provides fun provideProjectDao(db: AppDatabase): ProjectDao = db.projectDao()

  @Provides
  fun provideProjectWithGroupsDao(db: AppDatabase): ProjectWithGroupsDao = db.projectWithGroups()

  @Provides fun provideGroupRepository(dao: GrupoDao): GroupRepository = GroupRepositoryImpl(dao)

  @Provides
  fun provideProjectRepository(dao: ProjectDao): ProjectRepository = ProjectRepositoryImpl(dao)

  @Provides fun provideListAssociateRepository(): AssociateRepository = AssociateRepositoryImpl()

  @Provides fun provideEventRepository(): EventRepository = EventRepositoryImpl()
}
