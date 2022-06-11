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
            AnalyzeTextFields(
                entryReasonText,
                "Reason",
                "Reason for entry liquidity,fundamental...",
                onValueChange = onEntryReasonChange
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            AnalyzeTextFields(
                notesText,
                "Notes",
                "Additional notes of you",
                onValueChange = onNotesChange
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            AnalyzeTextFields(
                resultText,
                "Result",
                "Is operation/guess okay ?",
                onValueChange = onResultChange
            )

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape1(20.dp),
                ) { Text(text = "Add analyze", fontSize = 20.sp) }
            }
        }
    }
}

@Composable
fun AnalyzeTextFields(
    text: String,
    labelString: String,
    placeholderString: String,
    onValueChange: (String) ->