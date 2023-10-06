package com.rajeev.machinecoding.di

import android.app.Application
import com.google.gson.Gson
import com.rajeev.machinecoding.machinecoding.data.repository.QuizRepository
import com.rajeev.machinecoding.machinecoding.data.repository.QuizRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        app: Application
    ): QuizRepository {
        return QuizRepositoryImpl(
            app,
            Gson()
        )
    }

}