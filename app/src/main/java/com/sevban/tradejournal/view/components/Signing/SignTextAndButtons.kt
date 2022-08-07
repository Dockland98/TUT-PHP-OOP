package com.sevban.tradejournal.view.components.Signing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun EmailText(text: String, emailState: (String) -> Unit) {


    OutlinedTextField(
        value = text,
        onValueChange = emailState,
        label = { Text("E-mail") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            capitalization = KeyboardCapitalization.None
        ),
        colors = TextFieldDefaults
            .textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background
            )
    )
}

@Composable
fun PasswordText(text: String, passwordState: (String) -> Unit) {

    OutlinedTextField(
        value = text,
        onValueChange = passwordState,
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults
            .textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background
            )
    )
}

@Composable
fun GeneralButton(onClicked: () -> Unit, text: String, navController: NavController) {

    Surface(
        color = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.onBackground)
    ) {
        Button(
            onClick = onClicked,
            //colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(7.dp),
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun LessFocusedButton(onClicked: () -> Unit, text: String, navController: NavController) {

    Surface(
        color = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.onBackground)
    ) {
        Button(
        