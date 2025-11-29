package com.example.glamup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// simple model for mock items (all dummy)
data class Dispute(val title: String, val subtitle: String, val timeAgo: String, val priority: String)

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AdminPanel() {
    val disputes = listOf(
        Dispute("Payment Dispute", "Unauthorized charge for booking #4321", "2 hours ago", "High"),
        Dispute("Review Dispute", "Host disputes negative review", "5 hours ago", "Medium"),
        Dispute("Property Dispute", "Amenities differ from listing", "1 day ago", "Low")
    )

    Scaffold(topBar = { TopAppBar(title = { Text("Admin Panel") }) }) { pad ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            item {
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        StatCard("Total Users", "12,847", "▲ 2.5% from last month", Modifier.weight(1f))
                        Spacer(Modifier.width(12.dp))
                        StatCard("Active Bookings", "1,234", "▲ 1.1% from last week", Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth()) {
                        StatCard("Flagged Reviews", "23", "Needs attention", Modifier.weight(1f))
                        Spacer(Modifier.width(12.dp))
                        StatCard("Open Disputes", "8", "Pending resolution", Modifier.weight(1f))
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recent Disputes", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    TextButton(onClick = { /* no-op */ }) { Text("View All") }
                }
            }

            items(disputes) { d ->
                DisputeCard(d, Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
            }

            item {
                Spacer(Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Column(Modifier.weight(1f)) {
                        OutlinedButton(onClick = { /* no-op */ }, modifier = Modifier.fillMaxWidth()) { Text("Add User") }
                        Spacer(Modifier.height(8.dp))
                        OutlinedButton(onClick = { /* no-op */ }, modifier = Modifier.fillMaxWidth()) { Text("Reports") }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        OutlinedButton(onClick = { /* no-op */ }, modifier = Modifier.fillMaxWidth()) { Text("Block User") }
                        Spacer(Modifier.height(8.dp))
                        OutlinedButton(onClick = { /* no-op */ }, modifier = Modifier.fillMaxWidth()) { Text("Settings") }
                    }
                }
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(value, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun DisputeCard(d: Dispute, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(d.title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                AssistChip(onClick = {}, label = { Text(d.priority) })
            }
            Spacer(Modifier.height(4.dp))
            Text(d.subtitle, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(2.dp))
            Text(d.timeAgo, style = MaterialTheme.typography.bodySmall)
        }
    }
}
