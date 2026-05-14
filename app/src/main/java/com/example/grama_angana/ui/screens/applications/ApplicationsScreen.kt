package com.example.grama_angana.ui.screens.applications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsScreen(navController: NavController) {
    val applications = listOf(
        ApplicationData("Birth Records", "Approved", "2023-10-25"),
        ApplicationData("Citizen ID", "Pending", "2023-11-02"),
        ApplicationData("Property Services", "Approved", "2023-09-15"),
        ApplicationData("Grama Niladhari Certificate", "Rejected", "2023-11-10"),
        ApplicationData("Address Verification", "Approved", "2023-11-12")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Applications", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(applications) { app ->
                ApplicationCard(app)
            }
        }
    }
}

data class ApplicationData(val title: String, val status: String, val date: String)

@Composable
fun ApplicationCard(app: ApplicationData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Description,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "Submitted on: ${app.date}", style = MaterialTheme.typography.bodySmall)
            }
            StatusBadge(app.status)
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val containerColor = when (status) {
        "Approved" -> Color(0xFFE8F5E9)
        "Pending" -> Color(0xFFFFF3E0)
        "Rejected" -> Color(0xFFFFEBEE)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = when (status) {
        "Approved" -> Color(0xFF2E7D32)
        "Pending" -> Color(0xFFEF6C00)
        "Rejected" -> Color(0xFFC62828)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        color = containerColor,
        contentColor = contentColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
