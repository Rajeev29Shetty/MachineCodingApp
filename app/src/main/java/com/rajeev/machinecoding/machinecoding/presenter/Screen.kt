package com.rajeev.machinecoding.machinecoding.presenter

sealed class Screen(val route: String) {
    object QuizScreen: Screen("quiz_screen")
}
