package com.example.grama_angana.data.model

import com.google.firebase.Timestamp

data class Booking(
    val id: String = "",
    val name: String = "",
    val purpose: String = "",
    val date: String = "",
    val timestamp: Timestamp = Timestamp.now()
) {
    // This helper function turns our text entries into a format Firebase easily understands
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "purpose" to purpose,
            "date" to date,
            "timestamp" to timestamp
        )
    }
}