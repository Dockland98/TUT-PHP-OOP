
package com.sevban.tradejournal.repo.impl

import androidx.compose.runtime.MutableState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sevban.tradejournal.model.AnalyzeModel
import com.sevban.tradejournal.model.PairListModel
import com.sevban.tradejournal.model.User
import com.sevban.tradejournal.repo.ProfileRepoInterface
import com.sevban.tradejournal.util.Resource
import com.sevban.tradejournal.util.getRealTime
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

class ProfileRepository @Inject constructor(
    @Named("usersCollection")  private val usersCollectionReference: CollectionReference,
    private val firebase : FirebaseAuth
) : ProfileRepoInterface {

    override suspend fun saveImageId(imageState: Int) {

        val documentsSnapshot =
            usersCollectionReference.whereEqualTo("email", firebase.currentUser?.email).get().await()

        if (documentsSnapshot.documents.isNotEmpty()) {
            for (document in documentsSnapshot) {

                try {
                    usersCollectionReference.document(document.id).update("imageId", imageState)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    override suspend fun getUserModel(modelState: MutableState<User>) {

        usersCollectionReference.whereEqualTo("email", firebase.currentUser?.email )
            .getRealTime { getNextSnapshot ->
                while (true) {
                    val value = getNextSnapshot()

                    if (value != null) {
                        for (document in value) {
