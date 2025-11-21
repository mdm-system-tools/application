package org.MdmSystemTools.Application.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.view.screens.Meeting.TabsForRollCall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
	onClickMeetingBotton: (TabsForRollCall) -> Unit,
	onClickListRegisters: () -> Unit,
	onClickListEmployee: () -> Unit,
	viewModel: DashboardViewModel = hiltViewModel()
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Menu Principal") },
				navigationIcon = {
					IconButton(onClick = { /* TODO abrir drawer */ }) {
						Icon(Icons.Default.Menu, contentDescription = "Menu")
					}
				},
//				TODO Implementar tela de perfil do usuario
//        actions = {
//          IconButton(onClick = { /* perfil */ }) {
//            Icon(
//              painterResource(id = R.drawable.ic_launcher_background),
//              contentDescription = "Perfil",
//              tint = MaterialTheme.colorScheme.primary,
//            )
//          }
//        },
			)
		}
	) { paddingValues ->
		Column(
			modifier =
				Modifier
					.padding(paddingValues)
					.fillMaxSize()
					.background(Color(0xFFF6F7FF))
					.verticalScroll(rememberScrollState())
		) {
			HeaderSection()

			Spacer(Modifier.height(12.dp))

			Row(
				Modifier
					.fillMaxWidth()
					.padding(horizontal = 16.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				StatCard(title = "Projetos", count = uiState.amountProject, color = Color(0xFF3B82F6))
				StatCard(title = "Associados", count = uiState.amountAssociate, color = Color(0xFF8B5CF6))
				StatCard(title = "Concluídas", count = uiState.amountCompleted, color = Color(0xFF22C55E))
			}

			Spacer(Modifier.height(24.dp))

			QuickActionsSection(
				onNavigateToAttendance = { tab -> onClickMeetingBotton(tab) },
				onNavigateToRegisters = onClickListRegisters,
				onNavigateToEmployees = onClickListEmployee,
			)

			Spacer(Modifier.height(16.dp))

			Card(
				Modifier
					.padding(16.dp)
					.fillMaxWidth(),
				shape = RoundedCornerShape(16.dp),
				colors = CardDefaults.cardColors(containerColor = Color.White),
			) {
				Column(Modifier.padding(16.dp)) {
					Row(
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.SpaceBetween,
						modifier = Modifier.fillMaxWidth(),
					) {
						Row(verticalAlignment = Alignment.CenterVertically) {
							Icon(Icons.Default.Event, contentDescription = null)
							Spacer(Modifier.width(8.dp))
							Text("Próximas Reuniões", fontWeight = FontWeight.Bold)
						}
						Text(uiState.amountMeeting.toString())
					}

					Spacer(Modifier.height(12.dp))
					Column(
						horizontalAlignment = Alignment.CenterHorizontally,
						modifier = Modifier.fillMaxWidth(),
					) {
						Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray)
						Text("Nenhuma reunião agendada", color = Color.Gray)
						TextButton(onClick = { viewModel.openDialog() }) { Text("Criar primeira reunião") }
					}
				}

				if (uiState.showDialog) {
					Dialog(
						onDismissRequest = { viewModel.closeDialog() }
					) {
						Surface(
							shape = RoundedCornerShape(16.dp),
							tonalElevation = 8.dp,
							modifier = Modifier
								.fillMaxWidth(0.92f)
								.wrapContentHeight()
						) {
							LazyColumn(
								modifier = Modifier
									.padding(24.dp),
								verticalArrangement = Arrangement.spacedBy(16.dp)
							) {
								// Título
								item {
									Text(
										text = "Criar Evento",
										style = MaterialTheme.typography.titleLarge,
										fontWeight = FontWeight.Bold,
										modifier = Modifier.fillMaxWidth(),
										textAlign = TextAlign.Center
									)
								}

								// Projeto
								item {
									OutlinedTextField(
										state = uiState.project,
										label = { Text("Nome do Projeto *") },
										modifier = Modifier.fillMaxWidth()
									)
								}

								// Data
								item {
									Text("Data *", style = MaterialTheme.typography.labelLarge)
									DatePicker(
										state = uiState.date,
										modifier = Modifier.fillMaxWidth()
									)
								}

								// Hora
								item {
									Text("Horário *", style = MaterialTheme.typography.labelLarge)
									TimePicker(
										state = uiState.time,
										modifier = Modifier.fillMaxWidth()
									)
								}

								// Local
								item {
									OutlinedTextField(
										state = uiState.local,
										label = { Text("Local (interno/externo) *") },
										modifier = Modifier.fillMaxWidth()
									)
								}

								// Endereço
								item {
									OutlinedTextField(
										state = uiState.address,
										label = { Text("Endereço *") },
										modifier = Modifier.fillMaxWidth()
									)
								}

								// Botões
								item {
									Row(
										modifier = Modifier.fillMaxWidth(),
										horizontalArrangement = Arrangement.End
									) {
										TextButton(onClick = { viewModel.closeDialog() }) {
											Text("Cancelar")
										}
										Spacer(Modifier.width(12.dp))
										Button(onClick = {viewModel.closeDialog()}) {
											Text("salvar Evento")
										}
//										Button(
//											onClick = { viewModel.salvarEvento() },
//											enabled = uiState.isFormValid && !uiState.isSaving
//										) {
//											if (uiState.isSaving) {
//												CircularProgressIndicator(
//													modifier = Modifier.size(20.dp),
//													color = Color.White
//												)
//												Spacer(Modifier.width(8.dp))
//											}
//											Text("Salvar Evento")
//										}
									}
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
fun HeaderSection() {
	Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
		Image(
			painter = painterResource(id = R.drawable.logo_mdm),
			contentDescription = "Logo MDM",
			modifier = Modifier.size(64.dp),
		)
		Text(
			"Sistema de gerenciamento e controle de projetos",
			style = MaterialTheme.typography.bodyMedium,
			color = Color.Gray,
			textAlign = TextAlign.Center,
			modifier = Modifier.padding(horizontal = 32.dp),
		)
	}
}

@Composable
fun StatCard(title: String, count: Int, color: Color) {
	Card(
		modifier = Modifier.padding(4.dp),
		colors = CardDefaults.cardColors(containerColor = color),
		shape = RoundedCornerShape(16.dp),
	) {
		Column(
			Modifier.padding(vertical = 16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text("$count", style = MaterialTheme.typography.headlineMedium.copy(color = Color.White))
			Text(title, style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
		}
	}
}

@Composable
fun QuickActionsSection(
	onNavigateToAttendance: (TabsForRollCall) -> Unit,
	onNavigateToRegisters: () -> Unit,
	onNavigateToEmployees: () -> Unit,
) {
	Card(
		Modifier
			.padding(horizontal = 16.dp)
			.fillMaxWidth(),
		shape = RoundedCornerShape(16.dp),
		colors = CardDefaults.cardColors(containerColor = Color.White),
	) {
		Column(Modifier.padding(16.dp)) {
			Text(
				"Ações Rápidas",
				style = MaterialTheme.typography.titleMedium,
				fontWeight = FontWeight.Bold,
				modifier = Modifier.padding(bottom = 8.dp),
			)

			QuickActionButton("Chamada", Icons.Default.CalendarMonth, Color(0xFF8B5CF6)) {
				onNavigateToAttendance(TabsForRollCall.ROLLCALL)
			}
			QuickActionButton("Histórico de Chamada", Icons.Default.History, Color(0xFF8B5CF6)) {
				onNavigateToAttendance(TabsForRollCall.HISTORY)
			}
			QuickActionButton("Lista de Cadastros", Icons.AutoMirrored.Filled.List, Color(0xFF8B5CF6)) {
				onNavigateToRegisters()
			}
			QuickActionButton(
				"Gerenciamento de Funcionarios",
				Icons.Default.Groups,
				Color(0xFF8B5CF6)
			) {
				onNavigateToEmployees()
			}
		}
	}
}

@Composable
fun QuickActionButton(
	text: String,
	icon: ImageVector,
	backgroundColor: Color = Color(0xFFF3F4F6),
	onClick: () -> Unit,
) {
	Button(
		onClick = onClick,
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 4.dp),
		colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
		shape = RoundedCornerShape(12.dp),
	) {
		Row(
			Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
		) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Icon(icon, contentDescription = text)
				Spacer(Modifier.width(8.dp))
				Text(text)
			}
			Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
		}
	}
}