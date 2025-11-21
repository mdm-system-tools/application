package org.MdmSystemTools.Application.view.screens.Contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.view.components.FilterAndAddButton
import org.MdmSystemTools.Application.view.components.Profile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
	onBack: () -> Unit,
	onClickAdd: (TabsForContact) -> Unit,
	onClickItem: (Int, TabsForContact) -> Unit,
	viewModel: ContactViewModel = hiltViewModel(),
) {
	val tabs = TabsForContact.entries
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val pagerState: PagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })
	val coroutineScope: CoroutineScope = rememberCoroutineScope()

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Chamada") },
				navigationIcon = {
					IconButton(onClick = onBack) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
					}
				},
				//TODO implementar tela de perfil do usuario
//        actions = {
//          IconButton(onClick = { /* Perfil */ }) {
//            Icon(
//              Icons.Default.AccountCircle,
//              contentDescription = "Perfil",
//              tint = Color(0xFF7C3AED),
//            )
//          }
//        },
			)
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
				TabsForContact.entries.forEachIndexed { index, tab ->
					Tab(
						text = { Text(tab.title) },
						selected = pagerState.currentPage == index,
						onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
					)
				}
			}
			HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->

				val currentTab = tabs[page]

				Column(modifier = Modifier.fillMaxSize()) {
					FilterAndAddButton(currentTab) {
						when (currentTab) {
							TabsForContact.ASSOCIATE -> {
								if (viewModel.checkGroupListIsNotEmpty())
									onClickAdd(it)
							}

							TabsForContact.GROUP -> {
								if (viewModel.checkProjectListIsNotEmpty())
									onClickAdd(it)
							}

							TabsForContact.PROJECT -> onClickAdd(it)
						}
					}

					LazyColumn(modifier = Modifier.fillMaxSize()) {
						//TODO é possivel fazer melhor
						when (pagerState.currentPage) {
							0 -> {
								itemsIndexed(uiState.associates) { index, item ->
									Profile(item) {
										onClickItem(index, currentTab)
									}
								}
							}

							1 -> {
								itemsIndexed(uiState.groups) { index, item ->
									Profile(item) {
										onClickItem(index, currentTab)
									}
								}
							}

							2 -> {
								itemsIndexed(uiState.projects) { index, item ->
									Profile(item) {
										onClickItem(index, currentTab)
									}
								}
							}
						}
					}
				}

				if (uiState.showNoProjectDialog) {
					AlertDialog(
						onDismissRequest = { viewModel.closeNoProjectDialog() },
						title = { Text("Nenhum Projeto ativo no momento") },
						text = {
							Text("Você precisa cadastrar ou ativar pelo menos um projeto antes de adicionar um novo grupo.")
						},
						confirmButton = {
							TextButton(onClick = { viewModel.closeNoProjectDialog() }) {
								Text("Entendi")
							}
						},
//						dismissButton = {
//							TextButton(onClick = { viewModel.closeNoProjectDialog() }) {
//								Text("Criar projeto agora")
//							}
//						}
					)
				}

				if (uiState.showNoGroupDialog) {
					AlertDialog(
						onDismissRequest = { viewModel.closeNoGroupDialog() },
						title = { Text("Nenhum grupo ativo no momento") },
						text = {
							Text("Você precisa cadastrar ou ativar pelo menos um grupo antes de cadastrar novo associado.")
						},
						confirmButton = {
							TextButton(onClick = { viewModel.closeNoGroupDialog() }) {
								Text("entendi")
							}
						},
//						dismissButton = {
//							TextButton(onClick = { viewModel.closeNoGroupDialog() }) {
//								Text("Criar grupo")
//							}
//						}
					)
				}
			}
		}
	}
}
