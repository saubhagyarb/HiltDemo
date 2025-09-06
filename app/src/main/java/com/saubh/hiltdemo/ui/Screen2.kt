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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saubh.hiltdemo.AppDestinations

@Composable
fun Screen2(modifier: Modifier = Modifier, navController: NavController? = null) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is Screen 2", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController?.let {
                val newInstanceId = System.currentTimeMillis().toString()
                it.navigate("${AppDestinations.SCREEN_1}/$newInstanceId?showCard=false")
            }
        }) {
            Text("Go to Screen 1 from Screen 2")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            navController?.popBackStack()
        }) {
            Text("Back")
        }
    }
}