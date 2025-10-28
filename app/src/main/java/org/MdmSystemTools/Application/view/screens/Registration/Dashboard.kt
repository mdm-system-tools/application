package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.EventDate
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.view.components.Common.BannerTitle
import org.MdmSystemTools.Application.view.components.Common.CicleIcon
import org.MdmSystemTools.Application.view.components.Common.GridButtons

private data class Button(val icon: ImageVector, val title: String, val onClick: () -> Unit)

val meetings =
  listOf(
    EventDto(
      title = "teucunaminhamaeo",
      hourStart = "14:00",
      description = "seila",
      date = EventDate(12, 12, 2025),
    ),
    EventDto(
      title = "teucunaminhamaeo",
      hourStart = "14:00",
      description = "seila",
      date = EventDate(12, 12, 2025),
    ),
    EventDto(
      title = "teucunaminhamaeo",
      hourStart = "14:00",
      description = "seila",
      date = EventDate(12, 12, 2025),
    ),
  )

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DashBoard(
  onClickViewMeeting: () -> Unit,
  createAssociateButton: () -> Unit,
  listAssociateButton: () -> Unit,
) {
  Scaffold(
    topBar = {
      BannerTitle(
        R.string.dashboardAssociate_topbar_title,
        R.string.dashboardAssociate_topbar_subtitle,
      )
    }
  ) { paddingValues ->
    Column(
      Modifier.padding(paddingValues).fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      MenuMeetings(onClickViewMeeting)
      CardsInfo()
      DashboardActions(createAssociateButton, listAssociateButton)
    }
  }
}

@Composable
private fun CardsInfo() {
  Row {
    InfoCard("Associado", "42")
    InfoCard("Projetos", "10")
    InfoCard("Grupos", "85")
  }
}

@Composable
private fun MenuMeetings(onClickViewMeeting: () -> Unit) {
  Card(
    modifier = Modifier.padding(top = 10.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    shape = RoundedCornerShape(12.dp),
    border = BorderStroke(1.dp, Color.Gray),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
  ) {
    Column {
      Row {
        Text("Próximas Reunições")
        Spacer(modifier = Modifier.size(8.dp))
        Box(modifier = Modifier.padding(0.dp).clickable { onClickViewMeeting() }) {
          Text("Ver agenda", color = Color.Blue)
        }
      }
      LazyColumn { itemsIndexed(meetings) { index, meeting -> notifierMeeting(meeting) } }
      Row {
        Button(
          onClick = { println("Clicado!") },
          colors =
            ButtonDefaults.buttonColors(
              containerColor = Color(0xFF1C6AEA),
              contentColor = Color.White,
            ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.size(150.dp, 52.dp),
        ) {
          Text("Registrar Chamada")
        }

        Button(
          onClick = { println("Clicado!") },
          colors =
            ButtonDefaults.buttonColors(
              containerColor = Color(0xFF1C6AEA),
              contentColor = Color.White,
            ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.size(150.dp, 52.dp),
        ) {
          Text("Nova Reunião")
        }
      }
    }
  }
}

@Composable
fun InfoCard(title: String, value: String, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier.padding(8.dp).width(100.dp).height(80.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    shape = RoundedCornerShape(12.dp),
    border = BorderStroke(1.dp, Color.Gray),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
  ) {
    Column(
      modifier = Modifier.fillMaxSize().padding(8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = title, fontSize = 14.sp, color = Color.Gray)
      Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
  }
}

@Composable
private fun notifierMeeting(meeting: EventDto) {
  Row {
    Surface(
      modifier = Modifier.size(38.dp),
      shape = CircleShape,
      color = Color(0xFFDBEAFE),
      tonalElevation = 4.dp,
      shadowElevation = 6.dp,
    ) {
      CicleIcon(Icons.Default.CalendarToday, colorResource(R.color.azul2))
    }
    Column {
      Text(meeting.title)
      Text(meeting.description)
    }
    Column {
      Text(meeting.hourStart)
      Text(meeting.date.toString())
    }
  }
}

@Composable
fun DashboardActions(createAssociateButton: () -> Unit, listAssociateButton: () -> Unit) {
  var selectedTab by remember { mutableIntStateOf(0) }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
      listOf("Associados", "Grupos", "Projetos").forEachIndexed { index, title ->
        Button(
          onClick = { selectedTab = index },
          colors =
            ButtonDefaults.buttonColors(
              containerColor = if (selectedTab == index) Color(0xFF1C6AEA) else Color.Transparent,
              contentColor = if (selectedTab == index) Color.White else Color.Gray,
            ),
          border = BorderStroke(1.dp, Color.Gray),
          shape = RoundedCornerShape(8.dp),
        ) {
          Text(title)
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))
    val buttonsAssociates =
      listOf(
        Button(Icons.Default.Add, "Criar Associado", { createAssociateButton() }),
        Button(Icons.Default.AddAlarm, "Lista Associado", { listAssociateButton() }),
        Button(Icons.Default.AcUnit, "Historico de pagamento", {}),
        Button(Icons.Default.AddBox, "Atribuir Associado", {}),
      )

    val buttonsGroups =
      listOf(
        Button(Icons.Default.Add, "Criar Grupo", {}),
        Button(Icons.Default.AddAlarm, "Lista Grupo", {}),
        Button(Icons.Default.AcUnit, "Historico de pagamento", {}),
        Button(Icons.Default.AddBox, "Atribuir Grupo", {}),
      )

    val buttonsProjects =
      listOf(
        Button(Icons.Default.Add, "Criar Projeto", {}),
        Button(Icons.Default.AddAlarm, "Lista Projeto", {}),
        Button(Icons.Default.AcUnit, "Historico de pagamento", {}),
        Button(Icons.Default.AddBox, "Atribuir Projeto", {}),
      )

    when (selectedTab) {
      0 -> GridButtons(buttonsAssociates) { button -> CardButton(button) }
      1 -> GridButtons(buttonsGroups) { button -> CardButton(button) }
      2 -> GridButtons(buttonsProjects) { button -> CardButton(button) }
    }
  }
}

@Composable
private fun CardButton(cardButton: Button) {
  Card(
    onClick = cardButton.onClick,
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    shape = RoundedCornerShape(12.dp),
    border = BorderStroke(1.dp, Color.Gray),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
  ) {
    Column(
      Modifier.padding(20.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          modifier = Modifier.size(18.dp),
          imageVector = cardButton.icon,
          contentDescription = null,
          tint = Color(0xFF2563EB),
        )
      }
      Text(cardButton.title)
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
  DashBoard({}, {}, {})
}
