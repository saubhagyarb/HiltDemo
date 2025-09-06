package com.saubh.hiltdemo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saubh.hiltdemo.AppDestinations
import com.saubh.hiltdemo.utils.NotificationUtils
import kotlin.random.Random

@Composable
fun Screen1(
    modifier: Modifier = Modifier,
    showCard: Boolean = false,
    instanceId: String = "",
    navController: NavController? = null
) {
    val context = LocalContext.current

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

        Text(
            "Instance ID: $instanceId",
            style = MaterialTheme.typography.bodyMedium
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
                }
            }
        }

        Button(onClick = {
            navController?.let {
                val newInstanceId = System.currentTimeMillis().toString()
                it.navigate("${AppDestinations.SCREEN_1}/$newInstanceId?showCard=${!showCard}")
            }
        }) {
            Text(if (showCard) "Hide Card (New Version)" else "Show Card (New Version)")
        }

        Button(onClick = {
            navController?.let {
                NotificationUtils.showNotification(context, "screen1", 4)
                val newInstanceId = System.currentTimeMillis().toString()
                it.navigate("${AppDestinations.SCREEN_1}/$newInstanceId?showCard=true")
            }
        }) {
            Text("Open Another Screen 1 (Stacked)")
        }

        Button(onClick = {
            navController?.navigate(AppDestinations.SCREEN_2)
        }) {
            Text("Go to Screen 2")
        }

        Button(onClick = {
            navController?.popBackStack()
        }) {
            Text("Back")
        }
    }
}