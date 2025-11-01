package org.MdmSystemTools.Application.view.screens.Registration

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.view.components.Common.AssociateProfile

@Composable
fun AssociateListScreen(
  viewModel: AssociateListViewModel = hiltViewModel(),
  onClickAssociateProfile: (associateId: Int) -> Unit,
  onClickFloatingButton: () -> Unit,
  onClickBack: () -> Unit,
) {
  val listAssociates by viewModel.listAssociates.collectAsState()
  ListAssociates(onClickFloatingButton, listAssociates, onClickAssociateProfile, onClickBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListAssociates(
  onClickAdd: () -> Unit,
  listAssociates: List<AssociateDto>,
  onClickAssociateProfile: (Int) -> Unit,
  onClickBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Lista de Associados") },
        navigationIcon = {
          IconButton(onClickBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
        },
      )
    }
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      // SearchBar() // TODO Adicionar isso
      ButtonsActions(onClickAdd)
      LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(listAssociates) { index, associate ->
          Log.i("associateListScreen", "index de associado $index")
          AssociateProfile(associated = associate, onClick = { onClickAssociateProfile(index) })
        }
      }
    }
  }
}

@Composable
private fun ButtonsActions(onClick: () -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Card(
      // TODO Implementar uma filtro
      {},
      colors =
        CardDefaults.cardColors(
          containerColor = Color.Transparent,
          contentColor = colorResource(R.color.cinza2),
        ),
    ) {
      Icon(Icons.Default.FilterAlt, null)
      Text("Filtros")
    }
    Card(
      onClick,
      colors =
        CardDefaults.cardColors(
          containerColor = colorResource(R.color.azul),
          contentColor = Color.White,
        ),
    ) {
      Icon(Icons.Default.Add, null)
      Text("Adicionar")
    }
  }
}

@Preview
@Composable
private fun AssociateListScreenPreview() {
  val list = listOf(AssociateDto("jose", 1, 1), AssociateDto("maria", 1, 1))
  ListAssociates(
    onClickAssociateProfile = {},
    onClickAdd = {},
    listAssociates = list,
    onClickBack = {},
  )
}
