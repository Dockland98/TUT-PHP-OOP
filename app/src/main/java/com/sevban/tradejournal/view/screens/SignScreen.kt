
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 220.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    EmailText(emailText) { emailText = it }

                    Spacer(modifier = Modifier.padding(5.dp))

                    PasswordText(passwordText) { passwordText = it }

                    Spacer(modifier = Modifier.padding(20.dp))

                    GeneralButton(
                        onClicked = {
                            viewModel.signIn(
                                emailText,
                                passwordText,
                                context,
                                navController
                            )
                        },
                        text = "Sign In",
                        navController = navController
                    )
                    Spacer(modifier = Modifier.padding(20.dp))

                }

                IconButton(
                    onClick = {
                        navController.navigate(ScreenHolder.SignUpScreen.route)
                    },
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 256.dp)

                ) {
                    Row {
                        Text(text = "Register")
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Register button"
                        )
                    }
                }
            }
        }
    }
}