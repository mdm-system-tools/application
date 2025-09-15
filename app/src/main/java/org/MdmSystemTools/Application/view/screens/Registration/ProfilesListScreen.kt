package org.mdmsystemtools.application.presentation.ui.screens.Cadastro

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
import org.MdmSystemTools.Application.view.components.AssociatedProfile
import org.MdmSystemTools.Application.viewmodel.Registration.ListAssociatedViewModel
import org.mdmsystemtools.application.presentation.ui.components.ButtonFormAdd
import org.mdmsystemtools.application.presentation.ui.components.QuickAccessButtons
import org.mdmsystemtools.application.presentation.ui.components.SearchBar

@Composable
fun ProfilesListScreen(
  navController: NavHostController,
  viewModel: ListAssociatedViewModel = hiltViewModel(),
) {
  val listAssociateds by viewModel.listAssociateds.collectAsState()

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
        items(listAssociateds) { associated ->
          AssociatedProfile(associated)
        }
      }
    }
  }
}
