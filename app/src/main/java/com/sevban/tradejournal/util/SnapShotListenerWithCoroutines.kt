package com.sevban.tradejournal.util

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking

    suspend fun Query.getRealTime(block: suspend (getNextSnapshot: suspend () -> QuerySnapshot?) -> Unit) {
        val channel = Channel<Pair<QuerySnapshot?, FirebaseFirestoreException?>>(Channel.UNLIMITED)

        val listenerRegistration = this.addSnapshotListener { value, error ->
            channel.trySendBlocking(Pair(value, error))
        }

        try {
            block {
