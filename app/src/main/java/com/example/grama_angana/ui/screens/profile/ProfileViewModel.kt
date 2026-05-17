package com.example.grama_angana.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grama_angana.data.model.User
import com.example.grama_angana.data.repository.UserRepository
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
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProfileUiEvent>()
    val uiEvent: SharedFlow<ProfileUiEvent> = _uiEvent.asSharedFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getUserProfile()
                .onSuccess { user ->
                    _userState.value = user ?: User(
                        name = "Your Name",
                        email = "yourname@example.com",
                        phone = "+91 XXXXX XXXXX",
                        address = "Your Address"
                    )
                }
                .onFailure { error ->
                    _uiEvent.emit(ProfileUiEvent.ShowMessage("Failed to load profile: ${error.localizedMessage}"))
                }
            _isLoading.value = false
        }
    }

    fun updateUserProfile(name: String, email: String, phone: String, address: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val updatedUser = User(name = name, email = email, phone = phone, address = address)

            repository.saveUserProfile(updatedUser)
                .onSuccess {
                    _userState.value = updatedUser
                    _uiEvent.emit(ProfileUiEvent.UpdateSuccess)
                }
                .onFailure { error ->
                    _uiEvent.emit(ProfileUiEvent.ShowMessage("Failed to update: ${error.localizedMessage}"))
                }
            _isLoading.value = false
        }
    }
}

sealed class ProfileUiEvent {
    object UpdateSuccess : ProfileUiEvent()
    data class ShowMessage(val message: String) : ProfileUiEvent()
}