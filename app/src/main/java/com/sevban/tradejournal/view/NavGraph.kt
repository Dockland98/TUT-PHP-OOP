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
        startDestination = ScreenHolder.SigningScreen.route,
    ) {
        composable(ScreenHolder.SigningScreen.route) {
           SignScreen(navController = navController, context = context)

        }
        composable(ScreenHolder.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(ScreenHolder.SignUpScreen.route){
            SignUpScreen(context = context, navController = navController)
        }
        composable("pair_detail_screen/{currencyId}/{pairName}", arguments = listOf(
            navArgument("pairName"){
                type= NavType.StringType
            },
            navArgument("currencyId"){
                type= NavType.StringType
            }
        )){
            val pairName= remember {
                it.arguments?