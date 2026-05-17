package com.example.grama_angana.ui.screens.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grama_angana.data.model.Booking
import com.example.grama_angana.data.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: BookingRepository
) : ViewModel() {

    // Tracks if the app is currently uploading data to the cloud
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Used to send pop-up messages (Toasts) safely to the UI screen
    private val _uiEvent = MutableSharedFlow<BookingUiEvent>()
    val uiEvent: SharedFlow<BookingUiEvent> = _uiEvent.asSharedFlow()

    fun submitBooking(name: String, purpose: String, date: String) {
        // Validation: Don't let users submit empty spaces
        if (name.isBlank() || purpose.isBlank() || date.isBlank()) {
            viewModelScope.launch {
                _uiEvent.emit(BookingUiEvent.ShowToast("Please fill in all fields"))
            }
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val newBooking = Booking(name = name, purpose = purpose, date = date)

            repository.createBooking(newBooking)
                .onSuccess {
                    _uiEvent.emit(BookingUiEvent.BookingSuccess)
                }
                .onFailure { error ->
                    _uiEvent.emit(BookingUiEvent.ShowToast("Error: ${error.localizedMessage}"))
                }
            _isLoading.value = false
        }
    }
}

// Custom statuses our screen can react to
sealed class BookingUiEvent {
    object BookingSuccess : BookingUiEvent()
    data class ShowToast(val message: String) : BookingUiEvent()
}