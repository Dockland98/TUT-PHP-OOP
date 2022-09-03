
package com.sevban.tradejournal.view.screens

sealed class ScreenHolder(val route: String){
    object SigningScreen: ScreenHolder(route = "sign_screen")
    object ProfileScreen: ScreenHolder(route = "profile_screen")
    object SignUpScreen : ScreenHolder(route = "SignUpScreen")
    //object PairDetailScreen : ScreenHolder(route = "pair_detail_screen/{pairName}")
}