package org.mdmsystemtools.application.presentation.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mdmsystemtools.application.presentation.ui.components.ButtonFormAdd

//@Composable
//fun FactoryAssociated(associated: AssociatedEntity?) {
//	Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
//		Text("FOTO AQUI!!!!", modifier = Modifier.padding(end = 16.dp))
//		Column {
//			Text("Nome: ${associated?.name}")
//			Text("GroupID: ${associated?.groupId}")
//			Text("Numero Cartão: ${associated?.numberCard}")
//		}
//	}
//}
//
//@Composable
//fun ConnectAPI(
//	modifier: Modifier = Modifier,
//) {
//	//val associatesListState by viewModel.associatesList.collectAsState()
//	var isRefreshingManually by remember { mutableStateOf(false) }
//
//
//	Scaffold(modifier = modifier, bottomBar = {
//		ButtonFormAdd(modifier, onClick = {
//			//TODO Adicionar ação ao clicar no botão
//		})
//	}
//	) { paddingValues ->
//		Column(
//			modifier = Modifier
//        .fillMaxSize()
//        .padding(paddingValues)
//		) {
//			Row(
//				modifier = Modifier
//          .fillMaxWidth()
//          .padding(8.dp),
//				horizontalArrangement = Arrangement.End
//			) {
//				IconButton(
//					onClick = {
//						isRefreshingManually = true
//						//viewModel.refreshDataFromServer()
//					},
//					enabled = !isRefreshingManually
//				) {
//					if (isRefreshingManually) {
//						CircularProgressIndicator(modifier = Modifier.size(24.dp))
//						//TODO adicionar uma verificação de tempo e exibir uma mensagem de aviso
//					} else {
//						Icon(
//							imageVector = Icons.Filled.Refresh,
//							contentDescription = "Recarregar"
//						)
//					}
//				}
//			}
//
//			Box(
//				modifier = Modifier
//          .fillMaxSize()
//          .weight(1f),
//				contentAlignment = Alignment.Center
//			) {
//				//TODO adicionar a lista de associados
//			}
//		}
//	}
//}