package org.MdmSystemTools.Application.view.screens.Contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.MdmSystemTools.Application.view.components.FilterAndAddButton
import org.MdmSystemTools.Application.view.components.Profile

@Composable
fun ContactScreen(
  onClickAdd: (Tabs) -> Unit,
  onClickItem: (Int, Tabs) -> Unit,
  viewModel: ContactViewModel = hiltViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  Column(modifier = Modifier.fillMaxSize()) {
    PrimaryTabRow(selectedTabIndex = uiState.tabSelected.ordinal) {
      Tabs.entries.forEach { tab ->
        Tab(
          selected = uiState.tabSelected == tab,
          onClick = { viewModel.onTabSelected(tab) },
          text = { Text(tab.title) },
        )
      }
    }

    FilterAndAddButton(uiState.tabSelected) { onClickAdd(it) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
      itemsIndexed(uiState.list) { index, item ->
        when (item) {
          is ContactUiModel.Associate ->
            Profile(item.data) { onClickItem(index, uiState.tabSelected) }
          is ContactUiModel.Group -> Profile(item.data) { onClickItem(index, uiState.tabSelected) }
        }
      }
    }
  }
}
