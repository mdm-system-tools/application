package org.MdmSystemTools.Application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.AssociateRepositoryImpl
import org.MdmSystemTools.Application.model.repository.EventRepository
import org.MdmSystemTools.Application.model.repository.EventRepositoryImpl
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.model.repository.GroupRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  fun provideListAssociateRepository(): AssociateRepository {
    return AssociateRepositoryImpl()
  }

  @Provides
  @Singleton
  fun provideEventRepository(): EventRepository {
    return EventRepositoryImpl()
  }

  @Provides
  @Singleton
  fun provideGroupRepository(): GroupRepository {
    return GroupRepositoryImpl()
  }
}
