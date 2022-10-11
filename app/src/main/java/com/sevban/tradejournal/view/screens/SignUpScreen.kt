package com.sevban.tradejournal.view.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sevban.tradejournal.model.User
import com.sevban.tradejournal.view.components.Signing.EmailText
import com.sevban.tradejournal.view.components.Signing.LessFocusedButton
import com.sevban.tradejournal.view.components.Signing.PasswordText
import com.sevban.tradejournal.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(viewModel: SignUpViewModel= hiltViewModel(),navController: NavController,context: Context) {

    var emailText by remember{ mutableStateOf("") }
    var passwordText by remember{ mutableStateOf("") }
    var userNameText by remember{ mutableStateOf("")}
    val user = User(emailText,passwordText,userNameText,"")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.onBackground)
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp)
        ){

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 220.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value =userNameText,
                    label = { Text(text = "Username")},
                    onValueChange = {
                        userNameText=it
                        user.userName=it },
                    colors = TextFieldDefaults
                        .textFieldColors(
                            textColor = MaterialTheme.colors.onBackground,
                            backgroundColor = MaterialTheme.colors.background
                        )

                    )
                Spacer(modifier = Modifier.padding(