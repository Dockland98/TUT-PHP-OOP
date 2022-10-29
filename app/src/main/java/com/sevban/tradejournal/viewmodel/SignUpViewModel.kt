package com.sevban.tradejournal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sevban.tradejournal.model.User
import com.sevban.tradejournal.repo.SignUpRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel