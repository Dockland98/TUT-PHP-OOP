
package com.sevban.tradejournal.di


import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sevban.tradejournal.repo.*
import com.sevban.tradejournal.repo.impl.PairDetailRepository
import com.sevban.tradejournal.repo.impl.ProfileRepository
import com.sevban.tradejournal.repo.impl.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
