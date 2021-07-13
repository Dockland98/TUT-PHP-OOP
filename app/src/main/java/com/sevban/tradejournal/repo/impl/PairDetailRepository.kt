
package com.sevban.tradejournal.repo.impl

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sevban.tradejournal.model.AnalyzeModel
import com.sevban.tradejournal.repo.PairDetailRepoInterface
import com.sevban.tradejournal.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PairDetailRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
) : PairDetailRepoInterface {

    private val pairCollection = database.collection("Pairs")
        .document(auth.currentUser?.email.toString())
        .collection("Pair")

    /*override suspend fun getAnalysisListFromFirebase(
        analyzeList: MutableList<AnalyzeModel>,
        currentPairId: String
    ) {
        pairCollection
            .document(currentPairId)
            .collection("Analysis").orderBy("date", Query.Direction.DESCENDING)