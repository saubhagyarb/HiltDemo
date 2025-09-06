package com.saubh.hiltdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.saubh.hiltdemo.ui.AppNavigator
import com.saubh.hiltdemo.ui.theme.HiltDemoTheme
import com.saubh.hiltdemo.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint

object AppDestinations {
    const val MAIN_CONTENT = "main_content"
    const val SCREEN_1 = "screen1"
    const val SCREEN_2 = "screen2"
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var pendingNavigation: String? = null
    private var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationUtils.createNotificationChannels(this)

        setContent {
            HiltDemoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    this.navController = navController

                    LaunchedEffect(pendingNavigation) {
                        pendingNavigation?.let { destination ->
                            navController.navigate(destination)
                            pendingNavigation = null
                        }
                    }

                    AppNavigator(
                        navController = navController,
                        startDestination = AppDestinations.MAIN_CONTENT
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent.data?.let { uri ->
            when (uri.host) {
                "screen1" -> {
                    val showCard = uri.getQueryParameter("showCard")?.toBoolean() ?: false
                    val instanceId = uri.getQueryParameter("instanceId") ?: System.currentTimeMillis().toString()
                    pendingNavigation = "${AppDestinations.SCREEN_1}/$instanceId?showCard=$showCard"
                }
                "screen2" -> pendingNavigation = AppDestinations.SCREEN_2
            }
        }
    }
}