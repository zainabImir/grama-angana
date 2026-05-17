package com.example.grama_angana

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GramaAnganaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Force Firebase to initialize completely before any screen loads!
        FirebaseApp.initializeApp(this)
    }
}