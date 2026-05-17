package com.example.grama_angana.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCalendarScreen(
    navController: NavController,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val bookedDates by viewModel.bookedDates.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val currentMonth = remember { LocalDate.now() }
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = currentMonth.withDayOfMonth(1).dayOfWeek.value % 7

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hall Availability 🗓️", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                        Text(
                            text = day,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(firstDayOfWeek) {
                        Spacer(modifier = Modifier.size(40.dp))
                    }

                    items(daysInMonth) { index ->
                        val dayNumber = index + 1
                        val evaluationDate = currentMonth.withDayOfMonth(dayNumber)
                        val formattedDateString = evaluationDate.format(DateTimeFormatter.ISO_LOCAL_DATE) // "YYYY-MM-DD"

                        val isBooked = bookedDates.contains(formattedDateString)

                        CalendarDayCell(
                            dayNumber = dayNumber,
                            isBooked = isBooked,
                            onCellClick = {
                                if (!isBooked) {
                                    // Dynamically passes the strict machine string to the custom route argument
                                    navController.navigate("booking_form_route/$formattedDateString")
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Instructions:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("💡 Tap on any available green slot to launch the booking details form.", style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Legend Status:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(Color(0xFFE57373)))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Booked Slot (Unavailable)", style = MaterialTheme.typography.bodyMedium)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(Color(0xFFC8E6C9)))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Free Slot (Tap to book)", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDayCell(dayNumber: Int, isBooked: Boolean, onCellClick: () -> Unit) {
    val backgroundColor = if (isBooked) Color(0xFFFFCDD2) else Color(0xFFC8E6C9)
    val textColor = if (isBooked) Color(0xFFB71C1C) else Color(0xFF1B5E20)

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(enabled = !isBooked, onClick = onCellClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = dayNumber.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = if (isBooked) "Booked" else "Free",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isBooked) FontWeight.ExtraBold else FontWeight.Normal,
                color = textColor
            )
        }
    }
}