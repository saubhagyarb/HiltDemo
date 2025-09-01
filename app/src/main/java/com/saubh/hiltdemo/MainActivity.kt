package com.saubh.hiltdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saubh.hiltdemo.ui.Screen1
import com.saubh.hiltdemo.ui.Screen2
import com.saubh.hiltdemo.ui.main.MainViewModel
import com.saubh.hiltdemo.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint

// Define navigation routes
object AppDestinations {
    const val MAIN_CONTENT = "main_content"
    const val SCREEN_1 = "screen1"
    const val SCREEN_2 = "screen2"
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var navigateToFromNotification: String? = null
    private var isAppAlreadyOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAppAlreadyOpen = savedInstanceState != null // Simple check, could be more robust
        handleIntent(intent)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavigator(navController = navController, startDestination = AppDestinations.MAIN_CONTENT)

                    // Handle navigation from notification if the app was launched by it
                    LaunchedEffect(navigateToFromNotification, isAppAlreadyOpen) {
                        if (!isAppAlreadyOpen && navigateToFromNotification != null) {
                            navController.navigate(navigateToFromNotification!!)
                            navigateToFromNotification = null // Reset after navigation
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        isAppAlreadyOpen = true // App was already open when new intent arrived
        intent.let { handleIntent(it) }
    }

    private fun handleIntent(intent: Intent) {
        intent.getStringExtra("NAVIGATE_TO")?.let {
            navigateToFromNotification = it
        }
    }
}

@Composable
fun AppNavigator(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppDestinations.MAIN_CONTENT) {
            MainContentScreen(navController = navController)
        }
        composable(AppDestinations.SCREEN_1) {
            Screen1()
        }
        composable(AppDestinations.SCREEN_2) {
            Screen2()
        }
    }
}

@Composable
fun MainContentScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
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
            // Show notification first
            NotificationUtils.showNotification(context, AppDestinations.SCREEN_1, 1)
            // Then navigate
            navController.navigate(AppDestinations.SCREEN_1)
        }) {
            Text("Notify & Go to Screen 1")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            // Show notification first
            NotificationUtils.showNotification(context, AppDestinations.SCREEN_2, 2)
            // Then navigate
            navController.navigate(AppDestinations.SCREEN_2)
        }) {
            Text("Notify & Go to Screen 2")
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

// Renamed your original ApiContent to MainContentScreen and added NavController
// The original ApiContent composable is no longer directly used by setContent in MainActivity

