package com.sevban.tradejournal.util

import java.util.regex.Pattern

fun fetchIdOfImageFromURL(url: String?): String {
    if (url == null || !Pattern.matches("^https:\\/\\/www\\.tradingview\\.com\\/x\\/[A-Za-z0-9]+$", url))
        throw java.lang.Exception("Entered URL does not match with a tradingview url chart link.")
    val parts = 