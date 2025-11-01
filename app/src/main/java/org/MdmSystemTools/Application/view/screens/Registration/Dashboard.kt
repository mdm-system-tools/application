package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.view.components.BannerTitle
import org.MdmSystemTools.Application.view.components.CicleIcon
import org.MdmSystemTools.Application.view.screens.Calendar.EventListViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DashBoard(onClickViewMeeting: () -> Unit, viewModel: EventListViewModel = hiltViewModel()) {
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
      val events by viewModel.listEvents.collectAsState()
      MenuMeetings(onClickViewMeeting, events)
    }
  }
}

@Composable
private fun MenuMeetings(onClickViewMeeting: () -> Unit, meetings: List<EventDto>) {
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
    }
    Column {
      Text(meeting.hourStart)
      Text(meeting.date.toString())
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
  DashBoard({})
}
