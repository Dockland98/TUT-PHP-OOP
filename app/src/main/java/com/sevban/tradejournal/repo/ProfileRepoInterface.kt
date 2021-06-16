package com.sevban.tradejournal.repo

import androidx.compose.runtime.MutableState
import com.sevban.tradejournal.model.AnalyzeModel
import com.sevban.tradejournal.model.PairListModel
import com.sevban.tradejournal.model.User
import com.sevban.tradejournal.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow

interface ProfileRepoInterface {

    suspend fun saveImageId(imageState: Int)

    suspend fun getUserModel(modelState: 