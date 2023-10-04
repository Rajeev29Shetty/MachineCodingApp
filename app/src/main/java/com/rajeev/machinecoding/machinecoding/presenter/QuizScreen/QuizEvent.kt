package com.rajeev.machinecoding.machinecoding.presenter.QuizScreen

sealed class QuizEvent {
    data class onCharSelected(val char: Char) : QuizEvent()
    data class onItemSelected(val charId: Int) : QuizEvent()
    object clearAll : QuizEvent()
    object removeChar : QuizEvent()
    object nextQuestion : QuizEvent()
}
