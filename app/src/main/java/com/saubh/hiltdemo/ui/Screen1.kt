package com.saubh.hiltdemo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun Screen1(
    modifier: Modifier = Modifier,
    showCard: Boolean = false,
    onToggleCard: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            if (showCard) "Screen 1 with Card" else "Screen 1",
            style = MaterialTheme.typography.headlineMedium
        )

        if (showCard) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Random Things", style = MaterialTheme.typography.titleLarge)
                    Text("Random Number: ${Random.nextInt(1, 100)}")
                    Text("Random Boolean: ${Random.nextBoolean()}")
                    Text("Random Double: ${String.format("%.2f", Random.nextDouble())}")
                }
            }
        }

        Button(onClick = onToggleCard) {
            Text(if (showCard) "Hide Card" else "Show Card")
        }
    }
}
