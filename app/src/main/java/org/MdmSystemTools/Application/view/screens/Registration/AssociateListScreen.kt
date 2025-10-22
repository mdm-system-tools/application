package org.MdmSystemTools.Application.view.screens.Registration

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.view.components.Registration.AssociateProfile
import org.MdmSystemTools.Application.view.theme.AppConstants


@Composable
fun AssociateListScreen(
	viewModel: AssociateListViewModel = hiltViewModel(),
	onClickAssociateProfile: (associateId: Int) -> Unit,
	onClickFloatingButtom: () -> Unit
) {
	val listAssociates by viewModel.listAssociates.collectAsState()
	ListAssociates(onClickFloatingButtom, listAssociates, onClickAssociateProfile)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListAssociates(
	onClickFloatingButton: () -> Unit,
	listAssociates: List<AssociateDto>,
	onClickAssociateProfile: (Int) -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color(0xFF1C6AEA),
					titleContentColor = Color.White
				),
				title = {
					Column {
						Text(
							"Associate",
							fontSize = AppConstants.FontSize.large,
							fontWeight = FontWeight(500)
						)
						Text(
							"Gerencie Associados, Groupos e Projetos",
							fontSize = AppConstants.FontSize.medium,
							fontWeight = FontWeight(400)
						)
					}
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier.padding(innerPadding)
		) {
			//SearchBar() // TODO Adicionar isso
			ButtonsActions()
			LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
				itemsIndexed(listAssociates) { index, associate ->
					Log.i("associateListScreen", "index de associado $index")
					AssociateProfile(
						associated = associate,
						onClick = { onClickAssociateProfile(index) }
					)
				}
			}
		}
	}
}

@Composable
private fun ButtonsActions() {
	Row {
		Card(
			{}, colors = CardDefaults.cardColors(
				containerColor = Color.Transparent,
				contentColor = Color(0xFFB7B6B6)
			)
		) {
			Icon(Icons.Default.FilterAlt, null)
			Text("Filtros")
		}
		Card(
			{}, colors = CardDefaults.cardColors(
				containerColor = Color(0xFF1C6AEA),
				contentColor = Color.White
			)
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
	ListAssociates(onClickAssociateProfile = {}, onClickFloatingButton = {}, listAssociates = list)
}