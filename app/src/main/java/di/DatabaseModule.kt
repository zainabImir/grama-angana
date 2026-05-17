package com.example.grama_angana.di

import android.content.Context
import androidx.room.Room
import com.example.grama_angana.data.local.AppDatabase
import com.example.grama_angana.data.local.MaintenanceDao
import com.example.grama_angana.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "grama_angana_database"
        )
            .fallbackToDestructiveMigration() // Automatically refreshes local schemas during modifications
            .build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideMaintenanceDao(database: AppDatabase): MaintenanceDao {
        return database.maintenanceDao()
    }
}