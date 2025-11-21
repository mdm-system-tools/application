package org.MdmSystemTools.Application.view.screens

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.view.screens.Meeting.TabsForRollCall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
	onClickMeetingBotton: (TabsForRollCall) -> Unit,
	onClickListRegisters: () -> Unit,
	onClickListEmployee: () -> Unit,
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Menu Principal") },
				navigationIcon = {
					IconButton(onClick = { /* abrir drawer */ }) {
						Icon(Icons.Default.Menu, contentDescription = "Menu")
					}
				},
				//TODO Implementar tela de perfil do usuario
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

			// Cards principais
			Row(
				Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				StatCard(title = "Projetos", count = 4, color = Color(0xFF3B82F6))
				StatCard(title = "Associados", count = 32, color = Color(0xFF8B5CF6))
				StatCard(title = "Concluídas", count = 4, color = Color(0xFF22C55E))
			}

			Spacer(Modifier.height(24.dp))

			QuickActionsSection(
				onNavigateToAttendance = { tab -> onClickMeetingBotton(tab) },
				onNavigateToRegisters = onClickListRegisters,
				onNavigateToEmployees = onClickListEmployee,
			)

			Spacer(Modifier.height(16.dp))

			UpcomingMeetingsCard()
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
		Column(Modifier.padding(vertical = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
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
			QuickActionButton("Gerenciamento de Funcionarios", Icons.Default.Groups, Color(0xFF8B5CF6)) {
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

@Composable
fun UpcomingMeetingsCard() {
	Card(
		Modifier
      .padding(16.dp)
      .fillMaxWidth(),
		shape = RoundedCornerShape(16.dp),
		colors = CardDefaults.cardColors(containerColor = Color.White),
	) {
		var showDialog by remember { mutableStateOf(false) }
		if (showDialog) {
			CreateMeetingDialog(
				onDismiss = { showDialog = false },
				onSave = { project, date, local, address ->
					Log.d(
						"DashBoardScreen",
						"Projeto: $project | Data: $date | Local: $local | Endereço: $address"
					)
				},
			)
		}
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
				Text("0")
			}

			Spacer(Modifier.height(12.dp))
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth(),
			) {
				Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray)
				Text("Nenhuma reunião agendada", color = Color.Gray)
				TextButton(onClick = { showDialog = true }) { Text("Criar primeira reunião") }
			}
		}
	}
}

@Composable
fun CreateMeetingDialog(
	onDismiss: () -> Unit,
	onSave: (projectName: String, date: String, location: String, address: String) -> Unit,
) {
	var projectName by remember { mutableStateOf("") }
	var date by remember { mutableStateOf("") }
	var location by remember { mutableStateOf("") }
	var address by remember { mutableStateOf("") }

	Dialog(onDismissRequest = { onDismiss() }) {
		Card(
			shape = RoundedCornerShape(16.dp),
			modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
			colors = CardDefaults.cardColors(containerColor = Color.White),
		) {
			Column(Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState())) {
				Text(
					text = "Criar reunião",
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = Color.Black,
					modifier = Modifier.padding(bottom = 12.dp),
				)

				OutlinedTextField(
					value = projectName,
					onValueChange = { projectName = it },
					label = { Text("Selecionar Projeto") },
					placeholder = { Text("Nome do Projeto") },
					modifier = Modifier.fillMaxWidth(),
				)

				Spacer(Modifier.height(12.dp))

				OutlinedTextField(
					value = date,
					onValueChange = { date = it },
					label = { Text("Data") },
					placeholder = { Text("dd/mm/aaaa") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					modifier = Modifier.fillMaxWidth(),
				)

				Spacer(Modifier.height(12.dp))

				OutlinedTextField(
					value = location,
					onValueChange = { location = it },
					label = { Text("Local") },
					placeholder = { Text("ex: externo ou interno") },
					modifier = Modifier.fillMaxWidth(),
				)

				Spacer(Modifier.height(12.dp))

				OutlinedTextField(
					value = address,
					onValueChange = { address = it },
					label = { Text("Endereço") },
					placeholder = { Text("ex: R. Silva Bueno, 1991") },
					modifier = Modifier.fillMaxWidth(),
				)

				Spacer(Modifier.height(24.dp))

				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
					Button(
						onClick = {
							onSave(projectName, date, location, address)
							onDismiss()
						},
						colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDCCBFF)),
						modifier = Modifier.weight(1f),
					) {
						Text("Salvar")
					}

					Spacer(Modifier.width(8.dp))

					Button(
						onClick = { onDismiss() },
						colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3EFFF)),
						modifier = Modifier.weight(1f),
					) {
						Text("Cancelar", color = Color.Black)
					}
				}
			}
		}
	}
}