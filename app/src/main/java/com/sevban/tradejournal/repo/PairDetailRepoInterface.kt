package com.sevban.tradejournal.repo

import android.content.Context
import com.sevban.tradejournal.model.AnalyzeModel
import com.sevban.tradejournal.util.Resource
import kotlinx.coroutines.flow.Flow

interface PairDetailRepoInterface {

    fun getProductListFromFirestore(currentPairId: String): Flow<Resource<ArrayList<AnalyzeModel>>>

    //suspend fun getAnalysisListFromFirebase(analyzeList: MutableList<AnalyzeModel>, currentPairId: String)

    suspend fun saveA