package com.rajeev.machinecoding.machinecoding.data.repository

import android.content.Context
import com.google.gson.Gson
import com.rajeev.machinecoding.common.Resource
import com.rajeev.machinecoding.machinecoding.data.model.Quiz
import com.rajeev.machinecoding.machinecoding.data.model.QuizItem
import com.rajeev.machinecoding.machinecoding.util.ReadJsonFromAsset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class QuizRepositoryImpl @Inject constructor(
    val context: Context,
    val gson: Gson
) : QuizRepository {

    override suspend fun getQuizList(): Flow<Resource<List<QuizItem>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = ReadJsonFromAsset(context, "quiz.json")
                val quiz: Quiz = gson.fromJson(result, Quiz::class.java)
                emit(Resource.Success(quiz))
            } catch( e: Exception) {
                emit(Resource.Error(e.message ?: "Something Went wrong"))
            }
        }
    }
}