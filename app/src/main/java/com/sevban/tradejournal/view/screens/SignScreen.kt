
package com.sevban.tradejournal.view.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sevban.tradejournal.util.Resource
import com.sevban.tradejournal.view.components.Signing.EmailText
import com.sevban.tradejournal.view.components.Signing.GeneralButton
import com.sevban.tradejournal.view.components.Signing.PasswordText
import com.sevban.tradejournal.viewmodel.SignViewModel

@Composable
fun SignScreen(
    viewModel: SignViewModel = hiltViewModel(),
    navController: NavController,
    context: Context
) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    val showProgressBar by remember { viewModel.isLoading }

    LaunchedEffect(true) {

        when (viewModel.currentUserCheck()) {
            is Resource.Success -> {
                navController.navigate(ScreenHolder.ProfileScreen.route) {
                    popUpTo(ScreenHolder.SigningScreen.route) {
                        inclusive = true
                    }
                }
            }
            else -> {

            }
        }
    }

    if (showProgressBar) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp),

                ) {

                Column(