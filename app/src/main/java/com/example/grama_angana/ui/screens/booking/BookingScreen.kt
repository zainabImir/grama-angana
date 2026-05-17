package com.example.grama_angana.ui.screens.booking

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    navController: NavController,
    preSelectedDate: String // Safe string passed directly via NavGraph argument parsing
) {
    val context = LocalContext.current
    val firestore = remember { FirebaseFirestore.getInstance() }

    var name by remember { mutableStateOf("") }
    var purpose by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    val databaseFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val displayFormatter = remember { SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) }

    // Computes readable display label string seamlessly from incoming argument text
    val formattedDisplayDate = remember(preSelectedDate) {
        try {
            val parsedDate = databaseFormatter.parse(preSelectedDate)
            if (parsedDate != null) displayFormatter.format(parsedDate) else "No Date Chosen"
        } catch (e: Exception) {
            "No Date Chosen"
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hall Booking Form 🏛️", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Enter Reservation Details",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Name Input Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Name") },
                placeholder = { Text("e.g., Zain") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            // Purpose Input Field
            OutlinedTextField(
                value = purpose,
                onValueChange = { purpose = it },
                label = { Text("Purpose of Gathering") },
                placeholder = { Text("e.g., Wedding reception, Meeting") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                maxLines = 3
            )

            // Date Selection Card Display (Now Locked to the user's explicit calendar selection)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Selected Date",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = formattedDisplayDate,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    if (name.isBlank() || purpose.isBlank() || preSelectedDate.isBlank() || preSelectedDate == "No Date Chosen") {
                        Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isSubmitting = true

                    val bookingPayload = hashMapOf(
                        "name" to name.trim(),
                        "purpose" to purpose.trim(),
                        "date" to preSelectedDate, // Saves clean machine key string format ("2026-05-22")
                        "timestamp" to Timestamp.now()
                    )

                    // Write payload to Firestore
                    firestore.collection("bookings")
                        .add(bookingPayload)
                        .addOnSuccessListener {
                            isSubmitting = false
                            Toast.makeText(context, "Hall Booked Successfully! 🎉", Toast.LENGTH_LONG).show()

                            // Return back to the calendar view fresh
                            navController.popBackStack()
                        }
                        .addOnFailureListener { exception ->
                            isSubmitting = false
                            Toast.makeText(context, "Failed to book: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isSubmitting
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                } else {
                    Text("Confirm Booking", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}