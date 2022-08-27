
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