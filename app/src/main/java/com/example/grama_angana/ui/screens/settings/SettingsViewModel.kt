package com.example.grama_angana.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grama_angana.data.local.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {

    // Expose the theme flow cleanly to the view layout
    val isDarkModeEnabled: Flow<Boolean> = themePreferences.isDarkModeEnabled

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            themePreferences.saveThemeSetting(isDark)
        }
    }
}