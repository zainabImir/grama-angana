package com.example.grama_angana.ui.screens.maintainance

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.grama_angana.data.model.MaintenanceItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceJarScreen(
    viewModel: MaintenanceViewModel = hiltViewModel()
) {
    val items by viewModel.maintenanceItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Maintenance Jar 🍯", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items) { item ->
                MaintenanceCard(item = item, onPledgeClick = { amount ->
                    viewModel.pledge(item.id, amount)
                })
            }
        }
    }
}

@Composable
fun MaintenanceCard(item: MaintenanceItem, onPledgeClick: (Double) -> Unit) {
    val progress = if (item.targetAmount > 0) (item.raisedAmount / item.targetAmount).coerceIn(0.0, 1.0).toFloat() else 0f

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar tracking metrics exactly to success criteria specifications
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Raised: ₹${item.raisedAmount.toInt()}", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                Text(text = "Target: ₹${item.targetAmount.toInt()}", fontWeight = FontWeight.Normal)
            }

            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(10.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.outlineVariant,
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onPledgeClick(50.0) }, // Default pocket pledge step up increment of ₹50
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Pledge ₹50")
            }
        }
    }
}