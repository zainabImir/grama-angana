package com.example.grama_angana.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.grama_angana.data.model.MaintenanceItem

@Database(
    entities = [UserEntity::class, MaintenanceItem::class], // Both tables registered together
    version = 2,                                            // Bumped version to include the new table
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun maintenanceDao(): MaintenanceDao
}