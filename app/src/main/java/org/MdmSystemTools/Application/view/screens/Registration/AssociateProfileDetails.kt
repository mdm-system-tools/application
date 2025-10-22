package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.model.dto.AssociateDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssociateProfileDetails(
  id: Int?,
  onClickBackScreen: () -> Unit,
  viewModel: AssociateListViewModel = hiltViewModel()
) {
  if (id == -1) throw Exception("associate id invalido")
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text("Associate Profile")
        },
        navigationIcon = {
          IconButton(onClick = onClickBackScreen) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
          }
        }
      )
    }
  ) { paddingValues ->
    val assoc: AssociateDto = viewModel.getAssociate(id)
    val name = assoc.name
    val numberCard = assoc.numberCard
    val groupId = assoc.groupId

    Column(modifier = Modifier.padding(paddingValues)) {
      Row {
        Text(name)
        Text(numberCard.toString())
        Text(groupId.toString())
      }

    }
  }
}