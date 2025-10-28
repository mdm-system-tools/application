package org.MdmSystemTools.Application.view.screens.Meeting

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallToAction
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.view.components.Common.AlertDialog
import org.MdmSystemTools.Application.view.components.Common.BannerTitle
import org.MdmSystemTools.Application.view.components.Common.CicleIcon
import org.MdmSystemTools.Application.view.components.Common.GridButtons

data class button(
  val text: String,
  val icon: ImageVector?,
  val isSelected: Boolean,
  val onClick: () -> Unit = {},
)

@Composable
fun RollCall(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
  val showDialog = remember { mutableStateOf(false) }
  val associatesToRollCall =
    listOf(
      AssociateDto("jose", 1, 1),
      AssociateDto("jose", 1, 1),
      AssociateDto("jose", 1, 1),
      AssociateDto("jose", 1, 1),
      AssociateDto("jose", 1, 1),
      AssociateDto("jose", 1, 1),
      AssociateDto("jose", 1, 1),
    )

  Scaffold(
    topBar = { BannerTitle(R.string.rollcall_topbar_title, R.string.rollcall_topbar_subtitle) }
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(associatesToRollCall) { associate -> AssociateRollCall(associate) }
        item {
          Button(onClick = { showDialog.value = !showDialog.value }) { Text("Finalizar Chamada") }
        }
      }

      when {
        showDialog.value -> {
          AlertDialog(
            "Chamada",
            "Tem Certeza que deseja finalizar a ficha de chamada ??",
            Icons.Default.CallToAction,
            "Finalizar",
            "Voltar",
            { showDialog.value = !showDialog.value },
            { onClickBack() },
          )
        }
      }
    }
  }
}

@Composable
private fun AssociateRollCall(associate: AssociateDto) {
  val buttons =
    listOf(
      button("Presente", Icons.Default.Check, false, {}),
      button("Ausente", Icons.Default.Close, false, {}),
      button("Representante", Icons.Default.Person, false, {}),
      button("Limpar", null, false, {}),
    )

  Card(
    modifier = Modifier.padding(8.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    shape = RoundedCornerShape(12.dp),
    border = BorderStroke(1.dp, Color.Gray),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
  ) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
      AssociateInfo(associate)
      Text("Presente", color = Color.Blue, fontSize = 18.sp)
    }
    GridButtons(buttons) { button -> SelectableButton(button) }
  }
}

@Composable
private fun AssociateInfo(associate: AssociateDto) {
  Row {
    CicleIcon(Icons.Default.Person, colorResource(R.color.azul))
    Column {
      Text(associate.name, fontSize = 24.sp)
      Text(associate.groupId.toString(), fontSize = 18.sp)
    }
  }
}

@Composable
private fun SelectableButton(button: button) {
  val backgroundColor = if (button.isSelected) Color(0xFF1C6AEA) else Color(0xFFF3F4F6)
  val contentColor = if (button.isSelected) Color.White else Color.Black.copy(alpha = 0.8f)

  Button(
    onClick = button.onClick,
    colors =
      ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = contentColor),
    shape = RoundedCornerShape(12.dp),
    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
  ) {
    if (button.icon != null) {
      Icon(button.icon, contentDescription = null)
      Spacer(modifier = Modifier.width(6.dp))
    }
    Text(button.text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
  }
}

@Preview
@Composable
private fun RollCallPrev() {
  RollCall(onClickBack = {})
}
