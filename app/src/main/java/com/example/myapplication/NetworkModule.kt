package com.example.myapplication

import android.app.Application
import com.example.myapplication.repository.ListAssociatedRepository
import com.example.myapplication.repository.ListAssociatedRepositoryImpl
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
}