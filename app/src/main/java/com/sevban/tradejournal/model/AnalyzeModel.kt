
package com.sevban.tradejournal.model

import com.google.firebase.Timestamp
import com.google.type.Date

data class AnalyzeModel(
    var reason: String?="",
    var result: String?="",
    var notes: String?="",
    var rrRatio: Double?=0.0,
    var tradingViewUrl: String="",
    var id : String="",
    var date: java.util.Date? = Timestamp.now().toDate()
)
