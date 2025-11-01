package org.MdmSystemTools.Application.view.screens.Calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import java.util.Calendar
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.view.components.EventCard

@Composable
fun EventListScreen(
  viewModel: EventListViewModel = hiltViewModel(),
  onClickEventProfile: (eventId: Int) -> Unit,
  onClickFloatingButton: () -> Unit,
) {
  val listEvents by viewModel.listEvents.collectAsState()
  ListEvents(onClickFloatingButton, listEvents, onClickEventProfile)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListEvents(
  onClickAdd: () -> Unit,
  listEvents: List<EventDto>,
  onClickEventProfile: (Int) -> Unit,
) {
  Scaffold() { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      // SearchBar() // TODO Adicionar isso
      ButtonsActions(onClickAdd)
      LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(listEvents) { id, event ->
          EventCard(event = event, onClick = { onClickEventProfile(id) })
        }
      }
    }
  }
}

@Composable
private fun ButtonsActions(onClick: () -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Card(
      // TODO Implementar um filtro
      {},
      colors =
        CardDefaults.cardColors(
          containerColor = Color.Transparent,
          contentColor = Color(0xFFB7B6B6),
        ),
    ) {
      Icon(Icons.Default.FilterAlt, null)
      Text("Filtros")
    }
    Card(
      onClick,
      colors =
        CardDefaults.cardColors(containerColor = Color(0xFF1C6AEA), contentColor = Color.White),
    ) {
      Icon(Icons.Default.Add, null)
      Text("Adicionar")
    }
  }
}

@Preview
@Composable
private fun EventListScreenPreview() {
  val calendar =
    Calendar.getInstance().apply {
      set(2025, Calendar.OCTOBER, 10, 0, 0, 0)
      set(Calendar.MILLISECOND, 0)
    }

  val timestamp = calendar.timeInMillis
  val list =
    listOf(
      EventDto(
        title = "Reunião de Planejamento",
        date = timestamp,
        hourStart = "09:00",
        hourEnd = "10:30",
        local = "Sala de Reuniões",
        region = "Norte",
        project = "Website",
        groups = GroupDto("1", "Desenvolvimento", Color(0xFF1C6AEA)),
        color = Color(0xFF1C6AEA),
      ),
      EventDto(
        title = "Review Semanal",
        date = timestamp,
        hourStart = "14:00",
        hourEnd = "15:00",
        local = "Online",
        region = "Sul",
        project = "App Mobile",
        groups = GroupDto("2", "Design", Color(0xFFE91E63)),
        color = Color(0xFFE91E63),
      ),
    )
  ListEvents(onClickEventProfile = {}, onClickAdd = {}, listEvents = list)
}
