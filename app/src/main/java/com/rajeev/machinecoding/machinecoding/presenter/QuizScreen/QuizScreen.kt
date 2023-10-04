package com.rajeev.machinecoding.machinecoding.presenter.QuizScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.rajeev.machinecoding.machinecoding.presenter.QuizScreen.component.SelectedChar
import com.rajeev.machinecoding.machinecoding.presenter.QuizScreen.viewmodel.QuizViewModel
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizScreen(
    navController: NavController,
    quizViewModel: QuizViewModel = hiltViewModel()
) {
    val state = quizViewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        quizViewModel.eventFlow.collectLatest { event ->
            when(event) {
                is QuizUIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Log.d("Rajeev Checking", "${state.imageUrl}")
                Image(
                    painter = rememberAsyncImagePainter(state.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Spacer(modifier = Modifier.height(25.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    val chars = state.name?.toList()
                    chars?.forEachIndexed { index, char ->
                        val resultChar = if (state.selectedResult1.size >= index + 1)
                            state.selectedResult1.get(index) else null

                        SelectedChar(
                            char = resultChar,
                            modifier = Modifier.size(40.dp),
                            onCharSelected = state.itemSelectedToChange == index
                        ) {
                            quizViewModel.handleEvent(
                                QuizEvent.onItemSelected(
                                    index
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    val chars = state.availableCharacters?.toList()
                    chars?.forEachIndexed { index, char ->
                        SelectedChar(
                            char = char,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .size(40.dp)
                        ) {
                            quizViewModel.handleEvent(
                                QuizEvent.onCharSelected(
                                    char
                                )
                            )
                        }
                    }
                }

                if (state.resultMessage.isNotBlank()) {
                    Text(
                        text = state.resultMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    if (state.showReset) {
                        Button(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(5.dp)
                                .weight(0.5f),
                            onClick = {
                                quizViewModel.handleEvent(
                                    event = QuizEvent.removeChar
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                        ) {
                            Text(
                                text = "Reset Item",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                    }

                    if (state.showClear) {
                        Button(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(5.dp)
                                .weight(0.5f),
                            onClick = {
                                quizViewModel.handleEvent(
                                    event = QuizEvent.clearAll
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                        ) {
                            Text(
                                text = "Clear All",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    if (state.showNextQuestion) {
                        Button(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(5.dp)
                                .weight(1f),
                            onClick = {
                                quizViewModel.handleEvent(
                                    event = QuizEvent.nextQuestion
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                        ) {
                            Text(
                                text = "Next",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            if (state.errorMessage.isNotBlank()) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .align(Alignment.Center)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}