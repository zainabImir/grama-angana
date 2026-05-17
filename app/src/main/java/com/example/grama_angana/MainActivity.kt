package com.example.grama_angana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.grama_angana.data.local.ThemePreferences
import com.example.grama_angana.ui.screens.MainScreen
import com.example.grama_angana.ui.theme.GramaAnganaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Live-stream the theme preference straight out of DataStore storage
            val isDarkThemeSaved by themePreferences.isDarkModeEnabled.collectAsState(initial = false)

            GramaAnganaTheme(darkTheme = isDarkThemeSaved) {
                MainScreen()
            }
        }
    }
}