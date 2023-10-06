package com.rajeev.machinecoding

sealed class Screen(val route: String) {
    object QuizScreen: Screen("quiz_screen")
}
