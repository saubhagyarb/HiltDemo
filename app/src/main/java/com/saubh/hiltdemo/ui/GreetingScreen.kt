package com.saubh.hiltdemo.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GreetingScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val message = viewModel.getData()
    Text(text = message)
}