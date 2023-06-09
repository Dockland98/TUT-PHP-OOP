
package com.sevban.tradejournal.view.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.sevban.tradejournal.model.AnalyzeModel
import com.sevban.tradejournal.util.Resource
import com.sevban.tradejournal.util.fetchIdOfImageFromURL
import com.sevban.tradejournal.view.components.AddPairBottomSheet
import com.sevban.tradejournal.viewmodel.PairDetailViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun PairDetailScreen(
    currencyId: String,
    pairName: String,
    navController: NavController,
    urlFromIntent: String?,
    viewModel: PairDetailViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    var entryReasonText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var notesText by remember { mutableStateOf("") }
    var urlState by remember { mutableStateOf("") }

    if (urlFromIntent != null)
        urlState = urlFromIntent

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.onBackground)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                if (bottomScaffoldState.bottomSheetState.isCollapsed)
                    FloatingActionButton(
                        onClick = { scope.launch { sheetState.expand() } },
                        modifier = Modifier,
                        shape = CircleShape,
                        backgroundColor = MaterialTheme.colors.primary
                    ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add analyze") }
            },
            floatingActionButtonPosition = FabPosition.End

        ) { paddingValues ->
            BottomSheetScaffold(
                modifier = Modifier.padding(paddingValues),
                scaffoldState = bottomScaffoldState,
                sheetContent = {
                    AddPairBottomSheet(
                        urlState = urlState,
                        entryReasonText = entryReasonText,
                        resultText = resultText,
                        notesText = notesText,
                        onClick = {
                            scope.launch {
                                try {
                                    urlState = fetchIdOfImageFromURL(urlState)
                                } catch (e: java.lang.Exception) {
                                    bottomScaffoldState.snackbarHostState.showSnackbar(e.message.toString())
                                    return@launch
                                }
                                val analyze = AnalyzeModel(
                                    reason = entryReasonText,
                                    result = resultText,
                                    rrRatio = 0.0,
                                    tradingViewUrl = urlState,
                                    notes = notesText
                                )
                                sheetState.collapse()
                                viewModel.save(analyze, currencyId)
                            }
                        },
                        onEntryReasonChange = { entryReasonText = it },
                        onNotesChange = { notesText = it },
                        onResultChange = { resultText = it },
                        onUrlChange = { urlState = it }
                    )
                },
                sheetBackgroundColor = Color.LightGray,
                sheetPeekHeight = 0.dp,
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HeaderText(header = pairName, modifier = Modifier)

                        when (val listResponse = viewModel.analyzeListResponse) {

                            is Resource.Loading -> CircularProgressIndicator()
                            is Resource.Success -> LazyColumn {

                                items(
                                    items = listResponse.data!!,
                                    key = { item: AnalyzeModel -> item.id }) { analyze ->
                                    val dismissState = rememberDismissState(
                                        confirmStateChange = { dismissValue ->
                                            if (dismissValue == DismissValue.DismissedToStart) {
                                                scope.launch {
                                                    viewModel.deleteFromFirebase(
                                                        analyze,
                                                        listResponse.data,
                                                        currencyId
                                                    )
                                                }
                                            }
                                            true
                                        }
                                    )
                                    SwipeToDismiss(
                                        state = dismissState,
                                        directions = setOf(DismissDirection.EndToStart),
                                        modifier = Modifier.animateItemPlacement(),
                                        dismissThresholds = { direction ->
                                            FractionalThreshold(0.55f)
                                        },
                                        background = {
                                            val direction =
                                                dismissState.direction
                                            val color by animateColorAsState(
                                                targetValue = when (dismissState.targetValue) {
                                                    DismissValue.Default -> Color.LightGray
                                                    DismissValue.DismissedToEnd -> Color.Transparent
                                                    DismissValue.DismissedToStart -> Color.Red
                                                }
                                            )
                                            val icon = Icons.Default.Delete

                                            Box(
                                                modifier = Modifier
                                                    .fillParentMaxSize()
                                                    .background(color = color),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Icon(
                                                    imageVector = icon,
                                                    contentDescription = "Delete"
                                                )
                                            }
                                        },
                                        dismissContent = { AnalyzeRow(analyze = analyze) },
                                    )
                                }
                            }
                            is Resource.Error -> Text(text = "Liste verileri alınamadı.")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnalyzeRow(analyze: AnalyzeModel, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.onBackground)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

            Column(modifier = Modifier) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .border(BorderStroke(2.dp, color = MaterialTheme.colors.onBackground)),
                    verticalAlignment = CenterVertically
                ) {

                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Analyze",
                        )
                    }

                    Spacer(modifier = Modifier.padding(horizontal = 50.dp))

                    HeaderText(header = analyze.date.toString(), modifier = Modifier)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(analyze.tradingViewUrl)
                            .crossfade(true)
                            .build(),
                        loading = { CircularProgressIndicator() },
                        contentDescription = "Analyze",
                        modifier = Modifier.padding(5.dp)
                    )
                }
                AnalyzeRowText(
                    whatRow = "Notes: " + analyze.notes,
                    modifier = Modifier.padding(5.dp)
                )

                AnalyzeRowText(
                    whatRow = "Reason: " + analyze.reason,
                    modifier = Modifier.padding(5.dp)
                )

                AnalyzeRowText(
                    whatRow = "Result: " + analyze.result,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}


@Composable
fun AnalyzeRowText(whatRow: String, modifier: Modifier) {
    Text(
        text = whatRow,
        textAlign = TextAlign.Center,
        modifier = modifier,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Normal,
    )
}

@Composable
fun HeaderText(header: String, modifier: Modifier) {
    Text(
        text = header.trim(),
        textAlign = TextAlign.Center,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        maxLines = 1,
        style = MaterialTheme.typography.h5
    )
}