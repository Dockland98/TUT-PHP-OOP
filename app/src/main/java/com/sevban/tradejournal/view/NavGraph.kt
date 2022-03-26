package com.sevban.tradejournal.view


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sevban.tradejournal.view.screens.*


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    context: Context,
    urlFromIntent: String?
) {

    NavHost(
        navController = navController,
        startDest