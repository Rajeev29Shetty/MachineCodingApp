package com.rajeev.machinecoding.machinecoding.presenter.QuizScreen.viewmodel

import android.media.metrics.Event
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajeev.machinecoding.machinecoding.common.Resource
import com.rajeev.machinecoding.machinecoding.common.getRandomCharsIncludingString
import com.rajeev.machinecoding.machinecoding.data.model.QuizItem
import com.rajeev.machinecoding.machinecoding.data.repository.QuizRepository
import com.rajeev.machinecoding.machinecoding.presenter.QuizScreen.QuizEvent
import com.rajeev.machinecoding.machinecoding.presenter.QuizScreen.QuizState
import com.rajeev.machinecoding.machinecoding.presenter.QuizScreen.QuizUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    val quizRepository: QuizRepository
) : ViewModel() {

    var _state = mutableStateOf(QuizState())
    val state : MutableState<QuizState> = _state

    private val _eventFlow = MutableSharedFlow<QuizUIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var quizList = listOf<QuizItem>()

    init {
        getQuizList()
    }

    fun getQuizList() {
        viewModelScope.launch {
            quizRepository.getQuizList().collect { result ->
                when(result){
                    is Resource.Success -> {
                       result.data?.let {
                           quizList = it
                       }
                        _state.value = QuizState(
                            quizNo = _state.value.quizNo + 1,
                            imageUrl = quizList.get(_state.value.quizNo + 1).imgUrl,
                            name = quizList.get(_state.value.quizNo + 1).name,
                            availableCharacters = quizList.get(_state.value.quizNo + 1).name.getRandomCharsIncludingString()
                        )
                    }

                    is Resource.Error -> {
                        _state.value = QuizState(
                            errorMessage = result.message ?: "An Error Occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = QuizState(
                            isLoading = true
                        )
                    }
                }
            }

        }
    }

    fun handleEvent(event : QuizEvent) {
        when(event) {
            is QuizEvent.clearAll -> {
                _state.value = _state.value.copy(
                    selectedResult1 = mutableListOf(),
                    showClear = false,
                    itemSelectedToChange = -1,
                    showReset = false,
                )
            }
            is QuizEvent.removeChar -> {
                val resChar : MutableList<Char> = _state.value.selectedResult1.toMutableList()
                resChar.set(_state.value.itemSelectedToChange,Character.MIN_VALUE)

                _state.value = _state.value.copy(
                    selectedResult1 = resChar,
                    showReset = false,
                    itemSelectedToChange = -1
                )
            }
            is QuizEvent.onItemSelected -> {
                if(_state.value.showNextQuestion == false) {
                    _state.value = _state.value.copy(
                        showReset = if (_state.value.itemSelectedToChange == event.charIndex || _state.value.selectedResult1.size < event.charIndex + 1) false else true,
                        itemSelectedToChange = if (_state.value.itemSelectedToChange == event.charIndex || _state.value.selectedResult1.size < event.charIndex + 1) -1 else event.charIndex
                    )
                }
            }
            is QuizEvent.onCharSelected -> {
                val resChar : MutableList<Char> = _state.value.selectedResult1.toMutableList()
                var showNextQuestion = false
                val index = _state.value.selectedResult1.indexOfFirst { c -> c == Character.MIN_VALUE}
                if(index == -1) {
                    resChar.add(_state.value.selectedResult1.size, event.char)
                } else {
                    resChar.set(index, event.char)
                }

                val resultMessage =
                    if(resChar.size == (_state.value.name?.length ?: 0)) {
                        if(resChar.toCharArray().concatToString() == _state.value.name) {
                            showNextQuestion = true
                            "Result is correct"
                        } else
                            "Result is wrong. Try Again"
                    }
                    else if(resChar.size > (_state.value.name?.length ?: 0))
                        "Result is wrong. Try Again"
                    else ""

                if(_state.value.showNextQuestion == false) {
                    _state.value = _state.value.copy(
                        selectedResult1 = resChar,
                        resultMessage = resultMessage,
                        showClear = if(showNextQuestion) false else true,
                        showNextQuestion = showNextQuestion
                    )
                }
            }
            is QuizEvent.nextQuestion -> {
                if(quizList.size > _state.value.quizNo + 1) {
                    _state.value = QuizState(
                        quizNo = _state.value.quizNo + 1,
                        imageUrl = quizList.get(_state.value.quizNo + 1).imgUrl,
                        name = quizList.get(_state.value.quizNo + 1).name,
                        availableCharacters = quizList.get(_state.value.quizNo + 1).name.getRandomCharsIncludingString()
                    )
                } else {
                    viewModelScope.launch {
                        _eventFlow.emit(
                            QuizUIEvent.ShowSnackbar(
                                message = "All Questions done. Excellent!"
                            )
                        )
                    }
                }
            }
        }
    }
}