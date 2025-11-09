package org.MdmSystemTools.Application.view.screens.Calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
  id: Int,
  onClickBackScreen: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: EventListViewModel = hiltViewModel(),
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(stringResource(R.string.associateProfileDetails_topbar_title)) },
        navigationIcon = {
          IconButton(onClick = onClickBackScreen) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
          }
        },
      )
    }
  ) { paddingValues ->
    val event = viewModel.getEvent(id)
    Column(Modifier.padding(paddingValues)) {
      Text("Nome: ${event.title}")
      Text("Data: ${event.formatDate()}")
      Text("Horario de Inicio: ${event.hourStart}")
      Text("Horario do Fim: ${event.hourEnd}")
    }
  }
}
