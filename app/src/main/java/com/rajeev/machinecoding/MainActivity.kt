package com.rajeev.machinecoding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rajeev.machinecoding.machinecoding.presenter.QuizScreen.QuizScreen
import com.rajeev.machinecoding.machinecoding.presenter.Screen
import com.rajeev.machinecoding.ui.theme.MachineCodingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MachineCodingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.QuizScreen.route
                    ) {
                        composable(route = Screen.QuizScreen.route) {
                            QuizScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}



/*
1. Imageurl, name -> data
2. Random strings like 15 chars including name + remaining chars.
3.
*
* */

