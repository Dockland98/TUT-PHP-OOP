
package com.sevban.tradejournal.repo.impl

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sevban.tradejournal.model.User
import com.sevban.tradejournal.repo.SignUpRepoInterface
import com.sevban.tradejournal.view.screens.ScreenHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
): SignUpRepoInterface {
    override suspend fun createUser(user: User, navController: NavController) {

        val docRef = database.collection("Users").document()

        user.id = docRef.id

        try {

            docRef.set(user).await()

            auth.createUserWithEmailAndPassword(user.email, user.password)
                .await()

            auth.signInWithEmailAndPassword(user.email, user.password)
                .await()

            withContext(Dispatchers.Main) {
                navController.navigate(ScreenHolder.ProfileScreen.route) {
                    popUpTo(ScreenHolder.SignUpScreen.route) {
                        inclusive = true
                    }
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}