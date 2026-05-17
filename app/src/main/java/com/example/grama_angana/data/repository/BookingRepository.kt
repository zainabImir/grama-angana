package com.example.grama_angana.data.repository

import com.example.grama_angana.data.model.Booking
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    // This function takes our booking data and pushes it to a Firestore folder called "bookings"
    suspend fun createBooking(booking: Booking): Result<Unit> {
        return try {
            firestore.collection("bookings")
                .add(booking.toMap())
                .await() // This makes the app wait safely in the background until the cloud finishes saving
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}