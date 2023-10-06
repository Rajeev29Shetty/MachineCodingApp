package com.rajeev.machinecoding.machinecoding.data.repository

import com.rajeev.machinecoding.common.Resource
import com.rajeev.machinecoding.machinecoding.data.model.QuizItem
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getQuizList() : Flow<Resource<List<QuizItem>>>
}