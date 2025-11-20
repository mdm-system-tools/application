package org.MdmSystemTools.Application.view.screens.Contact.associate

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
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.view.components.AlertDialog

// TODO REFATORAR O CODIGO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssociateProfileDetails(
	id: Int,
	onClickBackScreen: () -> Unit,
	onClickEdit: () -> Unit,
	onCLickExport: () -> Unit,
	viewModel: AssociateDetailsViewModel = hiltViewModel(),
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(stringResource(R.string.associateProfileDetails_topbar_title)) },
        navigationIcon = {
          IconButton(onClick = onClickBackScreen) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
          }
        },
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
      modifier = Modifier.padding(paddingValues).fillMaxWidth(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      ImageAndInfoToAssociate(name, numberCard, groupId)
      ButtonsAction(onClickEdit, openAlertDialog, onCLickExport)
      ProgressionBar(title = "presença", icon = Icons.Default.CalendarToday, progresso)
      ProgressionBar(title = "pagamento", icon = Icons.Default.Payments, progresso)
      ObserverAlertDialog(openAlertDialog, id, viewModel::deleteAssociate, onClickBackScreen)
    }
  }
}

@Composable
private fun ObserverAlertDialog(
  openAlertDialog: MutableState<Boolean>,
  associateId: Int,
  deleteAssociate: (id: Int) -> Unit,
  onClickConfirmation: () -> Unit,
) {
  when {
    openAlertDialog.value -> {
      AlertDialog(
        onClickDismiss = { openAlertDialog.value = !openAlertDialog.value },
        onClickConfirm = {
          deleteAssociate(associateId, deleteAssociate)
          onClickConfirmation()
        },
        title = stringResource(R.string.associateProfileDetails_popup_title),
        text = stringResource(R.string.associateProfileDetails_popup_text),
        confirmButtonText = stringResource(R.string.associateProfileDetails_button_block),
        dismissButtonText = stringResource(R.string.associateProfileDetails_button_cancel),
        icon = Icons.Default.WarningAmber,
      )
    }
  }
}

@Composable
private fun ButtonsAction(
  onClickEdit: () -> Unit,
  openAlertDialog: MutableState<Boolean>,
  onCLickExport: () -> Unit,
) {
  Row(modifier = Modifier.fillMaxWidth()) {
    Button(onClickEdit) { Text(stringResource(R.string.associateProfileDetails_button_edit)) }
    Button(onClick = { openAlertDialog.value = !openAlertDialog.value }) {
      Text(stringResource(R.string.associateProfileDetails_button_block))
    }
    Button(onCLickExport) { Text(stringResource(R.string.associateProfileDetails_button_export)) }
  }
}

@Composable
private fun ImageAndInfoToAssociate(name: String, numberCard: Int, groupId: Int) {
  Image(
    painter = painterResource(id = R.drawable.ic_launcher_background),
    contentDescription = null,
    modifier = Modifier.padding(all = 6.dp).size(64.dp).clip(CircleShape),
  )
  Column {
    Text(stringResource(R.string.associateProfileDetails_info_name, name))
    Spacer(modifier = Modifier.width(8.dp))
    Text(stringResource(R.string.associateProfileDetails_info_numbercard, numberCard))
    Spacer(modifier = Modifier.width(8.dp))
    Text(stringResource(R.string.associateProfileDetails_info_group, groupId))
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
// TODO remover a bolinha ao final da barra
private fun ProgressionBar(title: String, icon: ImageVector, percent: Float) {
  Column {
    Row(
      modifier = Modifier.fillMaxWidth().padding(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      IconWithText(title, icon)
      PercentageText()
    }
    LinearProgressIndicator(
      progress = { percent },
      modifier = Modifier.fillMaxWidth().height(10.dp).clip(RoundedCornerShape(8.dp)),
      color = colorResource(R.color.azul),
      trackColor = colorResource(R.color.cinza),
      strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
    )
    Row(
      modifier = Modifier.fillMaxWidth().padding(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      // TODO coloca titulo
      TextWithSmallBall("...")
      TextWithSmallBall("...")
    }
  }
}

@Composable
private fun IconWithText(title: String, icon: ImageVector) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Icon(icon, null)
    Spacer(modifier = Modifier.width(8.dp))
    Text(title)
  }
}

@Composable
private fun PercentageText() {
  val percent = (10f / 20f) * 100
  Text("${percent.toInt()}%", fontSize = MaterialTheme.typography.bodySmall.fontSize)
}

@Composable
private fun TextWithSmallBall(label: String) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      "•",
      color = colorResource(R.color.azul),
      fontSize = MaterialTheme.typography.bodySmall.fontSize,
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(label, style = MaterialTheme.typography.bodyLarge)
  }
}
