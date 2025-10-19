package org.MdmSystemTools.Application

import android.app.Application
import org.MdmSystemTools.Application.model.repository.ListAssociateRepository
import org.MdmSystemTools.Application.model.repository.ListAssociateRepositoryImpl
import org.MdmSystemTools.Application.model.repository.EventRepository
import org.MdmSystemTools.Application.model.repository.EventRepositoryImpl
import org.MdmSystemTools.Application.model.repository.CalendarRepository
import org.MdmSystemTools.Application.model.repository.CalendarRepositoryImpl
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
  fun provideListAssociateRepository(): ListAssociateRepository {
    return ListAssociateRepositoryImpl()
  }

  @Provides
  @Singleton
  fun provideEventRepository(): EventRepository {
    return EventRepositoryImpl()
  }

  @Provides
  @Singleton
  fun provideCalendarRepository(): CalendarRepository {
    return CalendarRepositoryImpl()
  }
}