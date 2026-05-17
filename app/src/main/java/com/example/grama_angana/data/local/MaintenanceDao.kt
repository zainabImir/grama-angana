package com.example.grama_angana.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.grama_angana.data.model.MaintenanceItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {
    @Query("SELECT * FROM maintenance_items ORDER BY id DESC")
    fun getAllMaintenanceItems(): Flow<List<MaintenanceItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: MaintenanceItem)

    @Query("UPDATE maintenance_items SET raisedAmount = raisedAmount + :amount WHERE id = :itemId")
    suspend fun pledgeFunds(itemId: Int, amount: Double)
}