package com.sevban.tradejournal.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFireAuth()= FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireDB()= FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    @Named("pairCollection")
    fun providePairCollection(
        database: FirebaseFirestore,
        auth : FirebaseAuth
    ) =database.collection("Pairs")
        .document(auth.currentUser?.email.toString())
        .collection("Pair")

    @Provides
    @Singleton
    @Named("usersCollection")
    fun provideUsersCollection(database: FirebaseFirestore) =database.co