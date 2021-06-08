package com.sevban.tradejournal.repo

import android.content.Context
import com.sevban.tradejournal.model.AnalyzeModel
import com.sevban.tradejournal.util.Resource
import kotlinx.coroutines.flow.Flow

interface PairDetailRepoInterface {

    fun getProductListFromFirestore(currentPairId: String): Flow<Resource<ArrayList<AnalyzeModel>>>

    //suspend fun getAnalysisListFromFirebase(analyzeList: MutableList<AnalyzeModel>, currentPairId: String)

    suspend fun saveAnalyze(data: AnalyzeModel, currentPairId: String)

    suspend fun deleteFromFirebase(
        analyze: AnalyzeModel,
        analyzeList: ArrayList<AnalyzeModel>?,
        pairId: String,
        context: Context)
}