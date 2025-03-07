package com.example.bestiariodd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        content = { padding ->
            NavHost(
                navController = navController,
                startDestination = "monsterList",
                modifier = Modifier.padding(padding)
            ) {
                composable("monsterList") {
                    MonsterListScreen(navController = navController)
                }
                composable("monsterDetail/{monsterIndex}") { backStackEntry ->
                    val monsterIndex = backStackEntry.arguments?.getString("monsterIndex")

                    if (monsterIndex != null) {
                        MonsterDetailScreen(monsterIndex = monsterIndex)
                    }
                }
            }
        }
    )
}
