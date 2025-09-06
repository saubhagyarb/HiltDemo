package com.saubh.hiltdemo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.saubh.hiltdemo.AppDestinations

@Composable
fun AppNavigator(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppDestinations.MAIN_CONTENT) {
            MainContentScreen(navController)
        }

        composable(
            route = "${AppDestinations.SCREEN_1}/{instanceId}?showCard={showCard}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "hiltdemo://screen1?instanceId={instanceId}&showCard={showCard}"
                }
            ),
            arguments = listOf(
                navArgument("instanceId") {
                    type = NavType.StringType
                },
                navArgument("showCard") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            val showCard = entry.arguments?.getBoolean("showCard") ?: false
            Screen1(
                modifier = Modifier.fillMaxSize(),
                showCard = showCard,
                onToggleCard = {
                    navController.navigate("${AppDestinations.SCREEN_1}/${System.currentTimeMillis()}?showCard=${!showCard}")
                }
            )
        }

        composable(
            route = AppDestinations.SCREEN_2,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "hiltdemo://screen2"
                }
            )
        ) {
            Screen2()
        }
    }
}