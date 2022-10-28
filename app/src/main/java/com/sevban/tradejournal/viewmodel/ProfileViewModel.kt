
package com.sevban.tradejournal.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sevban.tradejournal.model.PairListModel
import com.sevban.tradejournal.model.User
import com.sevban.tradejournal.repo.ProfileRepoInterface
import com.sevban.tradejournal.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepoInterface,
    private val database: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    val modelState = mutableStateOf(User())
    var pairListResponse by mutableStateOf <List<PairListModel>>(listOf())
    val pairCountMap = mutableStateMapOf(Pair("",0))
    var isLoading by mutableStateOf(true)
    var initialList = listOf<PairListModel>()
    var isSearchStarting = true

    fun countOfAnalyze(currentPairId: String){

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val newRef = database.collection("Pairs")
                    .document(auth.currentUser?.email.toString())
                    .collection("Pair")
                    .document(currentPairId)
                    .collection("Analysis")

                val documents = newRef.get().await()

                val count = async {
                    documents.size()
                }
                pairCountMap[currentPairId] = count.await()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun searchPairs(query: String) {

        val listToSearch = if(isSearchStarting) {
            pairListResponse
        } else {
            initialList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                pairListResponse = initialList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.currency!!.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                initialList = pairListResponse
                isSearchStarting = false
            }
            pairListResponse = results
        }
    }

    fun saveImageId(imageState: Int) = CoroutineScope(Dispatchers.IO).launch {
        profileRepository.saveImageId(imageState)
    }

    fun getUserModel() = viewModelScope.launch {
        profileRepository.getUserModel(modelState)
    }

    fun signOut()= viewModelScope.launch {
        FirebaseAuth.getInstance().signOut()
    }

    fun addPair(newPair: String) = viewModelScope.launch {
        profileRepository.addPair(newPair)
    }

    fun getPairList() = viewModelScope.launch {
        profileRepository.getPairListFromFirestore().collect { response ->
            when (response) {
                is Resource.Success -> {
                    isLoading = false
                    pairListResponse = response.data!!
                }
                is Resource.Error -> {
                    isLoading = false
                    return@collect
                }
                is Resource.Loading -> {
                    isLoading = true
                }
            }
        }
    }

    fun searchPair() {
        profileRepository.searchPair()
    }

}
