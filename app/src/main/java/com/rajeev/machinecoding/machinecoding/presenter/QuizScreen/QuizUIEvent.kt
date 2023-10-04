package com.rajeev.machinecoding.machinecoding.presenter.QuizScreen

sealed class QuizUIEvent {
    data class ShowSnackbar(val message: String): QuizUIEvent()
}
