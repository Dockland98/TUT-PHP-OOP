package com.sevban.tradejournal.repo

import androidx.navigation.NavController
import com.sevban.tradejournal.model.User

interface SignUpRepoInterface {
    suspend fun createUser(user: User, navController: NavController)
}