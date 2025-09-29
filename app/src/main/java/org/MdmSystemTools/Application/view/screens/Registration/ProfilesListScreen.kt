package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.MdmSystemTools.Application.view.components.Registration.AssociateProfile
import org.MdmSystemTools.Application.viewmodel.Registration.ListAssociatedViewModel
import org.MdmSystemTools.Application.view.components.Common.ButtonFormAdd
import org.MdmSystemTools.Application.view.components.Registration.QuickAccessButtons
import org.MdmSystemTools.Application.view.components.Registration.SearchBar

@Composable
fun ProfilesListScreen(
  navController: NavHostController,
  viewModel: ListAssociatedViewModel = hiltViewModel(),
) {
  val listAssociates by viewModel.listAssociates.collectAsState()

  Surface {
    ButtonFormAdd(onClick = {
      navController.navigate("Formulario")
    })

    Column {
      SearchBar(
        placeholder = "Pesquisar cadastros...", onSearchChange = { searchText ->
        })

      QuickAccessButtons(
        onLastMeetingClick = { /* TODO */ },
        onLastSearchClick = { /* TODO */ },
      )

      LazyColumn(
        modifier = Modifier
          .padding(
            start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp
          )
      ) {
        items(listAssociates) { associate ->
          AssociateProfile(associate)
        }
      }
    }
  }
}
