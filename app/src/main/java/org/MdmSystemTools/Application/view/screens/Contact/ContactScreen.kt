package org.MdmSystemTools.Application.view.screens.Contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen

@Composable
fun ContactScreen(
  onClickAssociateProfile: (associateId: Int) -> Unit,
  onClickAdd: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val tabs = listOf("Associados", "Eventos", "Grupos")
  var selectedTab by remember { mutableIntStateOf(0) }
  Scaffold(
    topBar = {
      PrimaryTabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
          Tab(
            selected = selectedTab == index,
            onClick = { selectedTab = index },
            text = { Text(title) },
          )
        }
      }
    }
  ) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
      when (selectedTab) {
        0 -> AssociateListScreen(onClickAssociateProfile, onClickAdd)
      // 1 -> EventListScreen() // futuro
      // 2 -> GroupListScreen() // futuro
      }
    }
  }
}
