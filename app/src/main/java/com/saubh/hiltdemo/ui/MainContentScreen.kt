package com.saubh.hiltdemo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saubh.hiltdemo.AppDestinations
import com.saubh.hiltdemo.ui.main.MainViewModel
import com.saubh.hiltdemo.utils.NotificationUtils

@Composable
fun MainContentScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    val response by viewModel.response.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { viewModel.fetchData() }) {
            Text("Fetch Data")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            NotificationUtils.showNotification(context, "screen1", 1)
            // Create a new instance with unique ID to stack on navigation
            navController.navigate("${AppDestinations.SCREEN_1}/${System.currentTimeMillis()}?showCard=false")
        }) {
            Text("Go to Screen 1 (No Card)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            NotificationUtils.showNotification(context, "screen1_with_card", 2)
            // Create a new instance with unique ID to stack on navigation
            navController.navigate("${AppDestinations.SCREEN_1}/${System.currentTimeMillis()}?showCard=true")
        }) {
            Text("Go to Screen 1 (With Card)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            NotificationUtils.showNotification(context, AppDestinations.SCREEN_2, 3)
            navController.navigate(AppDestinations.SCREEN_2)
        }) {
            Text("Go to Screen 2")
        }

        Spacer(modifier = Modifier.height(16.dp))

        response?.let {
            Text("Response:\n${it.joinToString { item -> "${item.name} (ID: ${item.id})" }}")
        } ?: run {
            Text("Click the button to fetch data.")
        }

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}