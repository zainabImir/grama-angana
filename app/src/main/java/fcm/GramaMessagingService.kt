package com.example.grama_angana.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log

class GramaMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "Message received")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM", "Token: $token")
    }
}