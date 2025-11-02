package org.MdmSystemTools.Application.view.screens.Contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.view.components.ContactComponentes
import org.MdmSystemTools.Application.view.components.FilterAndAddButton
import org.MdmSystemTools.Application.view.components.GenericList

enum class Tabs(val title: String) {
  ASSOCIATE("Associados"),
  GROUP("Grupos"),
  PROJECT("Projetos"),
}

@Composable
fun ContactScreen(
  onClickAdd: (Tabs) -> Unit,
  onClickItem: (Int, Tabs) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: ContactViewModel = hiltViewModel(),
) {
  val componentes = ContactComponentes()
  var selectedTab by remember { mutableStateOf(Tabs.ASSOCIATE) }
  Scaffold(
    topBar = {
      PrimaryTabRow(selectedTabIndex = selectedTab.ordinal) {
        Tabs.entries.forEach { tab ->
          Tab(
            selected = selectedTab == tab,
            onClick = { selectedTab = tab },
            text = { Text(tab.title) },
          )
        }
      }
    }
  ) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
      when (selectedTab) {
        Tabs.ASSOCIATE -> {
          val items: List<AssociateDto> by viewModel.listAssociates.collectAsState()
					FilterAndAddButton(selectedTab) { onClickAdd(it) }
          GenericList(items, onClickItem = { id -> onClickItem(id, selectedTab) }) {
            item,
            index,
            onClick ->
            componentes.profile(item, onClick = { onClick(index) })
          }
        }
        Tabs.GROUP -> {
          val items: List<GroupDto> by viewModel.listGroup.collectAsState()
					FilterAndAddButton(selectedTab) { onClickAdd(it) }
          GenericList(items, onClickItem = { id -> onClickItem(id, selectedTab) }) {
            item,
            index,
            onClick ->
            componentes.profile(item, onClick = { onClick(index) })
          }
        }
        Tabs.PROJECT -> {
          Text("Projeto")
        }
      }
    }
  }
}
