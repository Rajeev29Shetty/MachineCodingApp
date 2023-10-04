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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    val quizRepository: QuizRepository
) : ViewModel() {

    var _state = mutableStateOf(QuizState())
    val state : MutableState<QuizState> = _state

    var quizList = listOf<QuizItem>()

    init {
        getQuizList()
    }

    fun getQuizList() {
        viewModelScope.launch {
            quizRepository.getQuizList().collect { result ->
                when(result){
                    is Resource.Success -> {
                        Log.d("Rajeev Checking", "${result.data}")
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

            }
            is QuizEvent.removeChar -> {

            }
            is QuizEvent.onItemSelected -> {

            }
            is QuizEvent.onCharSelected -> {
                val resultMessage =
                    if(_state.value.selectedResult.length+1 >= _state.value.name?.length?:0){
                        if(_state.value.selectedResult + event.char == _state.value.name)
                            "Result is correct"
                        else
                            "Result is wrong"
                    } else ""
                _state.value = _state.value.copy(
                    selectedResult = _state.value.selectedResult + event.char,
                    resultMessage = resultMessage
                )
            }
            is QuizEvent.nextQuestion -> {

            }
        }
    }
}