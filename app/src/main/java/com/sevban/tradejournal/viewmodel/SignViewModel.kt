package com.sevban.tradejournal.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.sevban.tradejournal.util.Resource
import com.sevban.tradejournal.view.screens.ScreenHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    var isLoading = mutableStateOf(true)

    fun signIn(
        email: String?,
        password: String?,
        context: Context,
        navController: NavController
    ) = viewModelScope.launch(Dispatchers.IO) {

        if (email != null && email.isNotBlank() && password != null && password.isNotBlank()) {

            try {
                auth.signInWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {

                    navController.navigate(ScreenHolder.ProfileScreen.route) {
                        popUpTo(ScreenHolder.SigningScreen.route) {
                            inclusive = true
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }

        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Lütfen email ve şifre alanlarını boş bırakmayınız.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun currentUserCheck() : Resource<Boolean> {

        if (auth.currentUser != null) {
            isLoading.value = false
            return Resource.Success(true)
        }
        else {
            isLoading.value = false
            return Resource.Error("")
        }
    }



    //Databaseden kullanıcı silince aynı zamanda Authenticationdan da silinmiş olmuyor.Bunu kontrol et ve silinmesini sağla.
    /*TODO()*/
    //Kullanıcıyı authenticationdan silsen bile signIn ekranını atlıyor bunu kontrol et.


}

