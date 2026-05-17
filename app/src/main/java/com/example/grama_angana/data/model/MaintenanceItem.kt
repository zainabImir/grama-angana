package com.example.grama_angana.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maintenance_items")
data class MaintenanceItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,          // e.g., "Need 10 LED Bulbs" or "Repairing Plastic Chairs"
    val description: String,    // e.g., "Main hall lighting is broken."
    val targetAmount: Double,   // Total money needed (e.g., 500.0)
    val raisedAmount: Double    // Total money pledged so far
)