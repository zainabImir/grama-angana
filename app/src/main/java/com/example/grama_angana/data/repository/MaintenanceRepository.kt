package com.example.grama_angana.data.repository

import com.example.grama_angana.data.local.MaintenanceDao
import com.example.grama_angana.data.model.MaintenanceItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaintenanceRepository @Inject constructor(
    private val maintenanceDao: MaintenanceDao
) {
    val allItems: Flow<List<MaintenanceItem>> = maintenanceDao.getAllMaintenanceItems()

    suspend fun addItem(item: MaintenanceItem) {
        maintenanceDao.insertItem(item)
    }

    suspend fun pledgeFunds(itemId: Int, amount: Double) {
        maintenanceDao.pledgeFunds(itemId, amount)
    }
}