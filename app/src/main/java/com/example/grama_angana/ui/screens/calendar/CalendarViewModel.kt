package com.example.grama_angana.ui.screens.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _bookedDates = MutableStateFlow<Set<String>>(emptySet())
    val bookedDates: StateFlow<Set<String>> = _bookedDates

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchBookedSlots()
    }

    fun fetchBookedSlots() {
        viewModelScope.launch {
            _isLoading.value = true
            firestore.collection("bookings")
                .get()
                .addOnSuccessListener { result ->
                    val datesSet = mutableSetOf<String>()
                    val isoFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    for (document in result) {
                        Log.d("CalendarDebug", "Document Data: ${document.data}")

                        // Safely retrieve the field as a generic Object to prevent type-cast crashes
                        val dateField = document.get("date") ?: document.get("bookingDate")

                        when (dateField) {
                            is String -> {
                                val normalized = normalizeDateString(dateField, isoFormatter)
                                if (normalized != null) datesSet.add(normalized)
                            }
                            is Timestamp -> {
                                val formattedTimestamp = isoFormatter.format(dateField.toDate())
                                datesSet.add(formattedTimestamp)
                            }
                            is java.util.Date -> {
                                val formattedDate = isoFormatter.format(dateField)
                                datesSet.add(formattedDate)
                            }
                        }
                    }

                    Log.d("CalendarDebug", "Final Parsed Booked Dates: $datesSet")
                    _bookedDates.value = datesSet
                    _isLoading.value = false
                }
                .addOnFailureListener { e ->
                    Log.e("CalendarDebug", "Error fetching bookings", e)
                    _isLoading.value = false
                }
        }
    }

    private fun normalizeDateString(dateStr: String, targetFormatter: SimpleDateFormat): String? {
        val formats = listOf("yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd")
        for (inputFormat in formats) {
            try {
                val parsedDate = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(dateStr)
                if (parsedDate != null) {
                    return targetFormatter.format(parsedDate)
                }
            } catch (e: Exception) {
                // Check next format structure
            }
        }
        return null
    }
}