package com.example.grama_angana.ui.screens.booking

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookingScreen() {
    // 1. Context and Database instance
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // 2. UI State
    var name by remember { mutableStateOf("") }
    var purpose by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // 3. Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Hall Booking",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            singleLine = true
        )

        // Purpose Input
        OutlinedTextField(
            value = purpose,
            onValueChange = { purpose = it },
            label = { Text("Purpose of Booking") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            singleLine = true
        )

        // Date Input (Simplified for now)
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (e.g., 15th June)") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            singleLine = true
        )

        Spacer(modifier = Modifier.weight(1f))

        // Submit Button
        Button(
            onClick = {
                if (name.isBlank() || purpose.isBlank() || date.isBlank()) {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {
                    isLoading = true

                    val bookingData = hashMapOf(
                        "name" to name,
                        "purpose" to purpose,
                        "date" to date,
                        "timestamp" to Timestamp.now()
                    )

                    db.collection("bookings")
                        .add(bookingData)
                        .addOnSuccessListener {
                            isLoading = false
                            // Clear fields after success
                            name = ""; purpose = ""; date = ""
                            Toast.makeText(context, "Booking Saved Successfully!", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { e ->
                            isLoading = false
                            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("CONFIRM BOOKING")
            }
        }
    }
}