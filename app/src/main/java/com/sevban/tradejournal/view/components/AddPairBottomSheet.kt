package com.sevban.tradejournal.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape as RoundedCornerShape1

@Composable
fun AddPairBottomSheet(
    urlState: String,
    entryReasonText: String,
    resultText: String,
    notesText: String,
    onClick: () -> Unit,
    onUrlChange: (String) -> Unit,
    onResultChange: (String) -> Unit,
    onEntryReasonChange: (String) -> Unit,
    onNotesChange: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnalyzeTextFields(
                urlState,
                labelString = "URL",
                placeholderString = "TradingView Chart Url",
                onValueChange = onUrlChange
            )

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
       