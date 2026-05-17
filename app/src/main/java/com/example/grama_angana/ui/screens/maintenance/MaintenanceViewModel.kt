package com.example.grama_angana.ui.screens.maintainance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grama_angana.data.model.MaintenanceItem
import com.example.grama_angana.data.repository.MaintenanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    private val repository: MaintenanceRepository
) : ViewModel() {

    // Converts our cold flow database queries into a hot state lifecycle flow for Compose
    val maintenanceItems: StateFlow<List<MaintenanceItem>> = repository.allItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Pre-populate with sample requirements if database is completely fresh
        viewModelScope.launch {
            repository.allItems.collect { list ->
                if (list.isEmpty()) {
                    repository.addItem(MaintenanceItem(title = "Need 10 Ceiling Bulbs", description = "Main wedding hall corner bulbs are completely fused.", targetAmount = 500.0, raisedAmount = 150.0))
                    repository.addItem(MaintenanceItem(title = "Repairing Plastic Chairs", description = "Fixing broken legs on 25 public seating assets.", targetAmount = 1200.0, raisedAmount = 900.0))
                    repository.addItem(MaintenanceItem(title = "New Stage Fan", description = "Panchayat assembly room needs a stable pedestal fan.", targetAmount = 2500.0, raisedAmount = 400.0))
                }
            }
        }
    }

    fun pledge(itemId: Int, amount: Double) {
        viewModelScope.launch {
            repository.pledgeFunds(itemId, amount)
        }
    }
}