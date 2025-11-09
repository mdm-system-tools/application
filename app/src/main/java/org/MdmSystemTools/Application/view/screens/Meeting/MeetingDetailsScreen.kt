package org.MdmSystemTools.Application.view.screens.Meeting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.view.components.BannerTitle

@Composable
fun MeetingDetailsScreen(onClickBack: () -> Unit, onClick: () -> Unit) {
  Scaffold(
    topBar = {
      BannerTitle(
        R.string.meetingdetails_topbar_title,
        R.string.meetingdetails_topbar_subtitle,
        true,
        onClickBack,
      )
    }
  ) { paddingValues ->
    Column(Modifier.padding(paddingValues)) {
      var selectedTab by remember { mutableIntStateOf(0) }
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        listOf(
            "Registrar Chamada"
            /*"Justificativas", "Histórico"*/
          )
          .forEachIndexed { index, title ->
            Button(
              onClick = { selectedTab = index },
              colors =
                ButtonDefaults.buttonColors(
                  containerColor =
                    if (selectedTab == index) Color(0xFF1C6AEA) else Color.Transparent,
                  contentColor = if (selectedTab == index) Color.White else Color.Gray,
                ),
              border = BorderStroke(1.dp, Color.Gray),
              shape = RoundedCornerShape(8.dp),
            ) {
              Text(title)
            }
          }
      }

      when (selectedTab) {
      // 0 -> RegisterMeeting(onClick = onClick)
      // 1 -> Actions(buttonsGroups) TODO Implementar tela de justificaitva
      // 2 -> Actions(buttonsProjects) TODO Implementar tela de historico
      }
    }
  }
}

// @Composable
// fun RegisterMeeting(modifier: Modifier = Modifier, onClick: () -> Unit) {
//  Text(text = "Selecione uma Reunião", fontSize = 24.sp)
//  // SearchBar() TODO Implementar barra de pesquisa
//  // TODO Implementar filtro, semelhante ao listassociate
//  LazyColumn { items(meetings) { meeting -> MeetingButton(meeting, onClick = onClick) } }
// }

@Composable
fun MeetingButton(meeting: EventDto, modifier: Modifier = Modifier, onClick: () -> Unit) {
  Card(
    modifier = modifier.padding(8.dp).fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    shape = RoundedCornerShape(12.dp),
    border = BorderStroke(1.dp, Color.Gray),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    onClick = onClick,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Column {
        Text(
          text = meeting.title,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = Color.Black,
        )
        Text(text = "${meeting.date} ás ${meeting.hourStart}", fontSize = 14.sp, color = Color.Gray)
      }
      Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun MeetingDetailsPrev() {
  MeetingDetailsScreen({}, {})
}
