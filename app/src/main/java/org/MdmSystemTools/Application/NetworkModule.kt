package org.MdmSystemTools.Application

import android.app.Application
import org.MdmSystemTools.Application.model.repository.ListAssociatedRepository
import org.MdmSystemTools.Application.model.repository.ListAssociatedRepositoryImpl
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
  fun provideListAssociatedRepository(app: Application): ListAssociatedRepository {
    return ListAssociatedRepositoryImpl(app)
  }

  @Provides
  @Singleton
  fun provideEventRepository(): EventRepository {
    return EventRepositoryImpl()
  }
}