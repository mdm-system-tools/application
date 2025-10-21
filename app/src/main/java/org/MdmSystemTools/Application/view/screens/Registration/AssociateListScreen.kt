package org.MdmSystemTools.Application.view.screens.Registration

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.view.components.Registration.AssociateProfile


@Composable
fun AssociateListScreen(
	viewModel: AssociateListViewModel = hiltViewModel(),
	onClickAssociateProfile: (associateId : Int) -> Unit,
	onClickFloatingButtom: () -> Unit
) {
	val listAssociates by viewModel.listAssociates.collectAsState()

	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				onClick = onClickFloatingButtom,
			) {
				Icon(Icons.Default.Add, contentDescription = "Adicionar")
			}
		}
	) { innerPadding ->
		Column(
			modifier = Modifier.padding(innerPadding)
		) {
			LazyColumn(
				modifier = Modifier
					.padding(
						start = 16.dp, end = 16.dp
					)
			) {
				itemsIndexed(listAssociates) { index, associate ->
					Log.i("associateListScreen", "index de associado $index")
					AssociateProfile(associate, { onClickAssociateProfile(index) })
				}
			}
		}
	}
}
