package com.rajeev.machinecoding.machinecoding.presenter.QuizScreen

data class QuizState(
    val quizNo: Int = -1,
    val imageUrl: String? = null,
    val name: String? = null,
    val selectedResult: String = "",
    val availableCharacters: List<Char> = emptyList(),
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val resultMessage: String = "",
)
