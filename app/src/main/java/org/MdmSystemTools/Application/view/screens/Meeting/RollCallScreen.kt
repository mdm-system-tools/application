package org.MdmSystemTools.Application.view.screens.Meeting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.GroupOff
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RollCallScreen(
	initialTab: TabsForRollCall,
	onBack: () -> Unit,
	onNavigateToStartGroup: (String) -> Unit,
	onViewHistoryItem: (String) -> Unit,
	viewModel: RollCallViewModel = hiltViewModel()
) {
	viewModel.changePage(initialTab.index)
	val uiState by viewModel.uiState.collectAsState()
	val pagerState: PagerState = rememberPagerState(initialPage = 1, pageCount = { 2 })

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Chamada") },
				navigationIcon = {
					IconButton(onClick = onBack) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
					}
				},
				//TODO Implementar tela de perfil do usuario
//				actions = {
//					IconButton(onClick = { /* Perfil */ }) {
//						Icon(
//							Icons.Default.AccountCircle,
//							contentDescription = "Perfil",
//							tint = Color(0xFF7C3AED),
//						)
//					}
//				},
			)
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
				.background(Color(0xFFF6F7FF))
		) {
			LaunchedEffect(uiState.targetPage) {
				pagerState.animateScrollToPage(uiState.targetPage)
			}
			PrimaryTabRow(selectedTabIndex = uiState.targetPage) {
				TabsForRollCall.entries.forEach { page ->
					Tab(
						text = { Text(page.title) },
						selected = uiState.targetPage == page.index,
						onClick = { viewModel.changePage(page.index) }
					)
				}
			}



			HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
				when (page) {
					0 -> RollCallTabContent(onStartGroup = onNavigateToStartGroup)
					1 -> HistoryTabContent(onViewHistoryItem = onViewHistoryItem)
				}
			}
		}
	}
}

@Composable
fun RollCallTabContent(onStartGroup: (String) -> Unit) {
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
		items(2) { index ->
			Card(
				shape = RoundedCornerShape(16.dp),
				colors = CardDefaults.cardColors(containerColor = Color.White),
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp),
			) {
				Column(Modifier.padding(16.dp)) {
					Text("🏁 Reunião do Projeto Vila Nova", fontWeight = FontWeight.Bold)
					Spacer(Modifier.height(4.dp))
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(Icons.Default.DateRange, contentDescription = null)
						Spacer(Modifier.width(8.dp))
						Text("22 de fevereiro de 2025")
					}

					Divider(Modifier.padding(vertical = 12.dp))

					Text("👥 Grupos (3)", fontWeight = FontWeight.Medium)
					Spacer(Modifier.height(8.dp))

					val groups = listOf("Grupo 9:00", "Grupo 10:00", "Grupo 18:00")
					groups.forEach { group ->
						OutlinedButton(
							onClick = { onStartGroup(group) },
							modifier = Modifier
								.fillMaxWidth()
								.padding(vertical = 4.dp),
							border = BorderStroke(1.dp, Color.Black),
							colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
						) {
							Row(
								modifier = Modifier.fillMaxWidth(),
								horizontalArrangement = Arrangement.SpaceBetween,
								verticalAlignment = Alignment.CenterVertically,
							) {
								Text(group)
								Row(verticalAlignment = Alignment.CenterVertically) {
									Icon(Icons.Default.PlayArrow, contentDescription = null)
									Text("Iniciar")
								}
							}
						}
					}
				}
			}
		}
	}
}

@Composable
fun HistoryTabContent(onViewHistoryItem: (String) -> Unit) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
		Card(
			shape = RoundedCornerShape(16.dp),
			colors = CardDefaults.cardColors(containerColor = Color.White),
			modifier = Modifier.fillMaxWidth(),
		) {
			Column(Modifier.padding(16.dp)) {
				Row(
					horizontalArrangement = Arrangement.SpaceBetween,
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
				) {
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(Icons.Default.Schedule, null)
						Spacer(Modifier.width(8.dp))
						Text("Próximas Reuniões")
					}
					Text("1", fontWeight = FontWeight.Bold)
				}
			}
		}

		Spacer(Modifier.height(16.dp))

		Card(
			shape = RoundedCornerShape(16.dp),
			colors = CardDefaults.cardColors(containerColor = Color.White),
			modifier = Modifier.fillMaxWidth(),
		) {
			Column(Modifier.padding(16.dp)) {
				Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
					StatusChip("Concluída", Color(0xFF22C55E))
					StatusChip("guarapiranga", Color.LightGray)
				}

				Spacer(Modifier.height(8.dp))

				Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
					Column {
						Text("Projeto", color = Color.Gray)
						Text("Projeto Beta")
					}
					Column {
						Text("Data", color = Color.Gray)
						Text("22/05/2020")
					}
				}

				Spacer(Modifier.height(8.dp))

				Row(
					horizontalArrangement = Arrangement.SpaceBetween,
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
				) {
					Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
						Row(verticalAlignment = Alignment.CenterVertically) {
							Icon(Icons.Default.Group, contentDescription = null, tint = Color(0xFF22C55E))
							Text("3")
						}
						Row(verticalAlignment = Alignment.CenterVertically) {
							Icon(Icons.Default.GroupOff, contentDescription = null, tint = Color.Red)
							Text("3")
						}
						Text("50%", color = Color(0xFFFFA500))
					}

					OutlinedButton(
						onClick = { onViewHistoryItem("Projeto Beta") },
						border = BorderStroke(1.dp, Color.Black),
						colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
					) {
						Icon(Icons.Default.Visibility, contentDescription = null)
						Spacer(Modifier.width(4.dp))
						Text("Ver")
					}
				}
			}
		}
	}
}

@Composable
fun StatusChip(text: String, color: Color) {
	Box(
		contentAlignment = Alignment.Center,
		modifier =
			Modifier
				.background(color.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
				.padding(horizontal = 8.dp, vertical = 4.dp),
	) {
		Text(text, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
	}
}