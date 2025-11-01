package org.MdmSystemTools.Application

import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.AssociateRepositoryImpl
import org.MdmSystemTools.Application.model.repository.EventRepository
import org.MdmSystemTools.Application.model.repository.EventRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}