package com.sevban.tradejournal.util

import java.util.regex.Pattern

fun fetchIdOfImageFromURL(url: String?): String {
    if (url == null || !Pattern.matches("^https:\\/\\/www\\.