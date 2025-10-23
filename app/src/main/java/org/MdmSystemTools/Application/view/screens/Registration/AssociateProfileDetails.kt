package org.MdmSystemTools.Application.view.screens.Registration

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.AssociateDto

//TODO REFATORAR O CODIGO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssociateProfileDetails(
	id: Int,
	onClickBackScreen: () -> Unit,
	viewModel: AssociateListViewModel = hiltViewModel(),
	onClickEdit: () -> Unit,
	onCLickExport: () -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text("Perfil do Associado")
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
		val openAlertDialog = remember { mutableStateOf(false) }
		val progresso = 0.5f

		Column(
			modifier = Modifier
        .padding(paddingValues)
        .fillMaxWidth(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			ImageAndInfoToAssociate(name, numberCard, groupId)
			ButtonsAction(onClickEdit, openAlertDialog, onCLickExport)
			ProgressionBar(progresso)
			ProgressionBar(progresso)
			ObserverAlertDialog(openAlertDialog, id, viewModel::deleteAssociate, onClickBackScreen)
		}
	}
}

@Composable
private fun ObserverAlertDialog(
	openAlertDialog: MutableState<Boolean>,
	associateId: Int,
	deleteAssociate: (id: Int) -> Unit,
	onClickConfirmation: () -> Unit
) {
	when {
		openAlertDialog.value -> {
			AlertDialogExample(
				onDismissRequest = { openAlertDialog.value = !openAlertDialog.value },
				onConfirmation = {
					deleteAssociate(associateId, deleteAssociate)
					onClickConfirmation()
				},
				dialogTitle = "Bloquear Associado",
				dialogText = "tem certeza que deseja bloquear esse associado ??",
				icon = Icons.Default.WarningAmber
			)
		}
	}
}

@Composable
private fun ButtonsAction(
	onClickEdit: () -> Unit,
	openAlertDialog: MutableState<Boolean>,
	onCLickExport: () -> Unit
) {
	Row(
		modifier = Modifier.fillMaxWidth(),
	) {
		Button(onClickEdit) {
			Text("Editar")
		}
		Button(
			onClick = { openAlertDialog.value = !openAlertDialog.value }
		) {
			Text("Bloquear")
		}
		Button(onCLickExport) {
			Text("Exportar")
		}
	}
}

@Composable
private fun ImageAndInfoToAssociate(name: String, numberCard: Int, groupId: Int) {
	Image(
		painter = painterResource(id = R.drawable.ic_launcher_background),
		contentDescription = null,
		modifier = Modifier
      .padding(all = 6.dp)
      .size(64.dp)
      .clip(CircleShape)
	)
	Row {
		Text("Nome do Associado $name")
		Spacer(modifier = Modifier.width(8.dp))
		Text("Numero da Carterinha $numberCard")
		Spacer(modifier = Modifier.width(8.dp))
		Text("Groupo $groupId")
	}
}

private fun deleteAssociate(id: Int, deleteAssociateFunc: (id: Int) -> Unit) {
	try {
		deleteAssociateFunc(id)
	} catch (e: Exception) {
		Log.e("ViewModelAssociateList", e.toString())
	}
}

@Composable
fun AlertDialogExample(
	onDismissRequest: () -> Unit,
	onConfirmation: () -> Unit,
	dialogTitle: String,
	dialogText: String,
	icon: ImageVector,
) {
	AlertDialog(
		icon = { Icon(icon, contentDescription = "Example Icon") },
		title = { Text(text = dialogTitle) },
		text = { Text(text = dialogText) },
		onDismissRequest = { onDismissRequest() },
		confirmButton = {
			TextButton(onClick = { onConfirmation() }) {
				Text("Bloquear")
			}
		},
		dismissButton = {
			TextButton(onClick = { onDismissRequest() }) {
				Text("Cancelar")
			}
		}
	)
}

@Composable
private fun ProgressionBar(progresso: Float) {
	Column {
		Row(
			modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			IconWithText()
			PercentageText()
		}
		LinearProgressIndicator(
			progress = { progresso },
			modifier = Modifier
        .fillMaxWidth()
        .height(10.dp)
        .clip(RoundedCornerShape(8.dp)),
			color = Color(0xFF1C6AEA),
			trackColor = Color(0xFFB0BEC5),
			strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
		)
		Row(
			modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			TextWithSmallBall()
			TextWithSmallBall()
		}
	}
}

@Composable
private fun IconWithText() {
	Row(verticalAlignment = Alignment.CenterVertically) {
		Icon(Icons.Default.CalendarToday, null)
		Spacer(modifier = Modifier.width(8.dp))
		Text("Frequencia")
	}
}

@Composable
private fun PercentageText() {
	val porcentagem = (10f / 20f) * 100
	Text("${porcentagem.toInt()}%")
}

@Composable
private fun TextWithSmallBall() {
	Row(
		verticalAlignment = Alignment.CenterVertically
	) {
		Text("•", color = Color(0xFF1C6AEA), fontSize = 20.sp)
		Spacer(modifier = Modifier.width(8.dp))
		Text("Alguma coisa", style = MaterialTheme.typography.bodyLarge)
	}
}