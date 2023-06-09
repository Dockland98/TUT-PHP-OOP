
package com.sevban.tradejournal.view.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sevban.tradejournal.R
import com.sevban.tradejournal.model.AnalyzeModel
import com.sevban.tradejournal.model.PairListModel
import com.sevban.tradejournal.view.DrawerBody
import com.sevban.tradejournal.view.DrawerHeader
import com.sevban.tradejournal.view.components.Drawer.MenuItem
import com.sevban.tradejournal.view.components.SearchBar
import com.sevban.tradejournal.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {

    val traders = arrayListOf(
        R.drawable.trader,
        R.drawable.trader1,
        R.drawable.trader2,
        R.drawable.trader3,
        R.drawable.trader4,
        R.drawable.trader5,
        R.drawable.trader6,
        R.drawable.trader7,
    )
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    viewModel.getUserModel()

    val model by remember { viewModel.modelState }
    var imageState by remember { mutableStateOf(R.drawable.trader) }
    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = 1) {
        viewModel.getPairList()
    }
    var pairList = viewModel.pairListResponse
    val isLoading = viewModel.isLoading

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.onBackground)
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else {

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    com.sevban.tradejournal.view.components.Drawer.AppBar(onNavigationItemClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })
                },
                drawerContent = {
                    DrawerHeader()
                    DrawerBody(items = listOf(
                        MenuItem(
                            id = "logout",
                            title = "Log Out",
                            contentDescription = "Log Out Button",
                            icon = Icons.Default.Logout
                        )
                    ), onItemClick = {
                        if (it.id == "logout") {
                            viewModel.signOut()
                            navController.navigate(ScreenHolder.SigningScreen.route)
                        }
                    })
                }

            ) { paddingValues ->
                BottomSheetScaffold(
                    modifier = Modifier.padding(paddingValues),
                    scaffoldState = bottomScaffoldState,
                    sheetContent = {

                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Choose an avatar", fontSize = 30.sp)
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .height(500.dp)
                                    .padding(16.dp),
                                columns = GridCells.Adaptive(80.dp),
                                content = {

                                    items(traders) { i ->
                                        Box(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .aspectRatio(1f)
                                                .clip(RoundedCornerShape(5.dp)),
                                            contentAlignment = Alignment.Center

                                        ) {
                                            Image(
                                                painterResource(id = i),
                                                contentDescription = "Avatar",
                                                Modifier
                                                    .clickable {
                                                        scope.launch {

                                                            imageState = i
                                                            viewModel.saveImageId(imageState)

                                                        }
                                                    }
                                                    .border(
                                                        BorderStroke(
                                                            2.dp,
                                                            color = MaterialTheme.colors.primary
                                                        )
                                                    )
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    },
                    sheetBackgroundColor = Color.LightGray,
                    sheetPeekHeight = 0.dp,
                    sheetShape = RoundedCornerShape(30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxWidth()
                    ) {
                        Row {

                            Image(
                                bitmap = ImageBitmap.imageResource(model.imageId),
                                contentDescription = "User Avatar",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .weight(2f)
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .clickable {
                                        scope.launch {

                                            if (sheetState.isExpanded) {
                                                sheetState.collapse()
                                            } else {
                                                sheetState.expand()
                                            }
                                        }
                                    }
                            )

                            Box(
                                modifier = Modifier
                                    .weight(3f)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = model.userName,
                                        modifier = Modifier.padding(start = 35.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text(
                                        text = "Overall win rate: ",
                                        modifier = Modifier.padding(start = 35.dp),
                                        fontSize = 15.sp
                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text(
                                        text = "Most traded: ",
                                        modifier = Modifier.padding(start = 35.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            }
                        }


                        Row(modifier = Modifier.fillMaxWidth()) {
                            SearchBar(
                                hint = "Search...", modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(2f)
                                    .padding(5.dp)
                            ) { searchString ->
                                viewModel.searchPairs(searchString)
                            }
                            Surface(
                                color = MaterialTheme.colors.background,
                                contentColor = contentColorFor(MaterialTheme.colors.onBackground)
                            ) {
                                Button(
                                    onClick = {
                                        openDialog = true
                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f),
                                    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                                    colors = ButtonDefaults.outlinedButtonColors(Color.White),
                                    shape = CircleShape,

                                    ) {

                                    Image(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add a pair",
                                        modifier = Modifier.size(25.dp)
                                    )

                                    Text(text = "Add", Modifier.padding(start = 10.dp))
                                }
                            }


                            var currencyName by remember { mutableStateOf("") }
                            if (openDialog) {

                                Surface(
                                    color = MaterialTheme.colors.background,
                                    contentColor = MaterialTheme.colors.onBackground
                                ) {
                                    Dialog(
                                        onDismissRequest = { openDialog = false },
                                        properties = DialogProperties(
                                            dismissOnBackPress = false,
                                            dismissOnClickOutside = true
                                        ),
                                    ) {
                                        Surface(
                                            color = MaterialTheme.colors.background,
                                            contentColor = MaterialTheme.colors.onBackground
                                        ) {
                                            Box(modifier = Modifier) {
                                                Column(modifier = Modifier.padding(11.dp)) {
                                                    Text(
                                                        text = "Add pair",
                                                        fontSize = MaterialTheme.typography.h5.fontSize,
                                                        fontWeight = Bold,
                                                    )
                                                    Spacer(modifier = Modifier.padding(top = 12.dp))
                                                    Text(
                                                        text = "Enter the pair you want to add",
                                                        fontSize = MaterialTheme.typography.h6.fontSize,
                                                        fontWeight = Normal,
                                                    )

                                                    TextField(
                                                        modifier = Modifier
                                                            .padding(22.dp)
                                                            .border(
                                                                BorderStroke(
                                                                    2.dp,
                                                                    MaterialTheme.colors.primary
                                                                ), RectangleShape
                                                            ),
                                                        value = currencyName,
                                                        shape = RoundedCornerShape(6.dp),
                                                        onValueChange = { currencyName = it },
                                                        colors = TextFieldDefaults.textFieldColors(
                                                            backgroundColor = Color.Transparent,
                                                            focusedIndicatorColor = Color.Transparent,
                                                            unfocusedIndicatorColor = Color.Transparent
                                                        ),
                                                        label = { Text(text = "Pair") },
                                                        placeholder = { Text(text = "BTC/USD") }
                                                    )

                                                    Row(
                                                        modifier = Modifier
                                                            .padding(4.dp)
                                                            .align(Alignment.CenterHorizontally)
                                                    ) {
                                                        TextButton(onClick = { openDialog = false }) {
                                                            Text(text = "Cancel")

                                                        }

                                                        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
                                                        TextButton(
                                                            colors = ButtonDefaults.buttonColors(
                                                                backgroundColor = MaterialTheme.colors.primary
                                                            ),
                                                            shape = RoundedCornerShape(20.dp),
                                                            onClick = {
                                                                scope.launch {
                                                                    viewModel.addPair(currencyName)
                                                                }
                                                                openDialog = false
                                                            }) {

                                                            Text(text = "Add")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        LazyColumn(modifier = Modifier.padding(5.dp)) {
                            items(pairList ,key = { item: PairListModel -> item.id!! }) { currency ->
                                CurrencyRow(currency = currency, navController, viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyRow(
    currency: PairListModel,
    navController: NavController,
    viewModel: ProfileViewModel
) {
    viewModel.countOfAnalyze(currency.id!!)
    val currencyName = if (currency.currency != null) {
        currency.currency!!.uppercase()
    } else {
        ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(2.dp, MaterialTheme.colors.onBackground), RoundedCornerShape(2.dp))
            .clickable {
                val encodedUrl = URLEncoder
                    .encode(currency.currency, StandardCharsets.UTF_8.toString())
                    .uppercase()
                navController.navigate("pair_detail_screen/${currency.id}/${encodedUrl}")
            }
    ) {
        Text(
            text = currencyName,
            fontSize = 24.sp,
            fontWeight = ExtraBold,
            modifier = Modifier
                .padding(16.dp)
                .weight(3f)
        )

        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = "Analysis : ${viewModel.pairCountMap[currency.id]?: 0}  ",
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Win rate : "/*TODO*/,
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
    Spacer(modifier = Modifier.padding(4.dp))
}

@Composable
fun Alert() {
    var openDialog by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = { openDialog = false },
        title = { Text(text = "Add Pair", fontSize = 24.sp, color = Color.Black) },
        text = { Text(text = "Enter the pair you want to add") },
        confirmButton = {
            TextButton(onClick = {
                openDialog = false
            }) {
                Text(text = "Add")

            }
        },
        dismissButton = {
            TextButton(onClick = { openDialog = false }) {
                Text(text = "Cancel")
            }
        }

    )
}

@Composable
fun OutlinedIconButton(modifier: Modifier, onClick: () -> Unit, buttonText: String) {
    Button(
        onClick = onClick,
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        colors = ButtonDefaults.outlinedButtonColors(Color.White),
        shape = CircleShape,

        ) {

        Image(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a pair",
            modifier = Modifier.size(25.dp)
        )

        Text(text = buttonText, Modifier.padding(start = 10.dp))
    }
}