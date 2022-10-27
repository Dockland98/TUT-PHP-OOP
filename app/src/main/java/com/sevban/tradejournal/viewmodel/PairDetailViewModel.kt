
package com.sevban.tradejournal.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevban.tradejournal.model.AnalyzeModel
import com.sevban.tradejournal.repo.PairDetailRepoInterface
import com.sevban.tradejournal.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PairDetailViewModel @Inject constructor(
    private val pairDetailRepository: PairDetailRepoInterface,
    private val application: Application,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var analyzeListResponse by mutableStateOf <Resource<ArrayList<AnalyzeModel>>> (Resource.Loading())
        private set

    init {
        val currentPairId = savedStateHandle.get<String>("currencyId")
        getProductList(currentPairId ?: "-1")
        println(currentPairId)
    }

    fun getProductList(currentPairId: String) = viewModelScope.launch {
        pairDetailRepository.getProductListFromFirestore(currentPairId).collect { response ->
            analyzeListResponse = response
        }
    }

    fun save(data: AnalyzeModel,currentPairId: String)= viewModelScope.launch {
        pairDetailRepository.saveAnalyze(data,currentPairId)
    }

    fun deleteFromFirebase(analyze: AnalyzeModel, analyzeList: ArrayList<AnalyzeModel>?, pairId: String)= viewModelScope.launch  {
        pairDetailRepository.deleteFromFirebase(analyze,analyzeList,pairId,application)
    }
}