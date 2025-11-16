package org.MdmSystemTools.Application.view.screens.Contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.view.components.FilterAndAddButton
import org.MdmSystemTools.Application.view.components.Profile
import org.MdmSystemTools.Application.view.screens.Contact.projetc.ProjetoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
  onBack: () -> Unit,
  onClickAdd: (Tabs) -> Unit,
  onClickItem: (Int, Tabs) -> Unit,
  viewModel: ContactViewModel = hiltViewModel(),
) {
  val tabs = Tabs.entries
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val pagerState: PagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })
  val coroutineScope: CoroutineScope = rememberCoroutineScope()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Chamada") },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
          }
        },
        actions = {
          IconButton(onClick = { /* Perfil */ }) {
            Icon(
              Icons.Default.AccountCircle,
              contentDescription = "Perfil",
              tint = Color(0xFF7C3AED),
            )
          }
        },
      )
    }
  ) { paddingValues ->
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
      PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
        Tabs.entries.forEachIndexed { index, tab ->
          Tab(
            text = { Text(tab.title) },
            selected = pagerState.currentPage == index,
            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
          )
        }
      }
      HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->

        val currentTab = tabs[page]

        when (currentTab) {
          Tabs.PROJECT -> {
            ProjetoScreen(
              onBackClick = onBack,
              onProjetoClick = {},
              onAddProjetoClick = { onClickAdd(currentTab) }
            )
          }
          else -> {
            Column(modifier = Modifier.fillMaxSize()) {
              FilterAndAddButton(currentTab) { onClickAdd(it) }

              LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(uiState.list) { index, item ->
                  when (item) {
                    is ContactUiModel.Associate -> Profile(item.data) { onClickItem(index, currentTab) }

                    is ContactUiModel.Group -> Profile(item.data) { onClickItem(index, currentTab) }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
