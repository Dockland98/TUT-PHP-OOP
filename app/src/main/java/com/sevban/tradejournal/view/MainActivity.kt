package com.sevban.tradejournal.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sevban.tradejournal.ui.theme.TradeJournalTheme
import com.sevban.tradejournal.view.screens.PairDetailScreen
import com.sevban.tradejournal.view.screens.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val urlFromIntent = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (urlFromIntent != null)
            println(urlFromIntent)
        else
            println("url is nullllllllllllll")

        setContent {
            TradeJournalTheme {
                navController = rememberNavController()
                    Setu