package org.MdmSystemTools.Application.view.screens.Calendar

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.RealEstateAgent
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.view.components.Common.FieldDropdownMenuStyled
import org.MdmSystemTools.Application.view.components.EventField
import org.MdmSystemTools.Application.view.components.Common.LocalSelector
import org.MdmSystemTools.Application.view.components.TimePickerDialog
import org.MdmSystemTools.Application.view.screens.Meeting.UiEvent
import org.MdmSystemTools.Application.view.theme.AppConstants

@Preview
@Composable
private fun AddEventScreenPreview() {
  EventFormScreen({}, {})
}

private val regioesPredefinidas = listOf("Norte", "Nordeste", "Centro-Oeste", "Sudeste", "Sul")
private val grupos =
  listOf(
    "Desenvolvimento",
    "Design",
    "Marketing",
    "Vendas",
    "Suporte",
    "Recursos Humanos",
    "Financeiro",
    "Operações",
  )

private val projetosPredefinidos =
  listOf(
    "Sistema MDM",
    "Portal Web",
    "Aplicativo Mobile",
    "Sistema de Gestão",
    "Plataforma E-commerce",
    "Sistema de CRM",
    "Dashboard Analytics",
    "API Gateway",
    "Sistema de Monitoramento",
    "Plataforma de Treinamento",
  )

// TODO FEATURE adicionar botão para apagar evento
// TODO FEATURE adicionar efeitos de voltar e fechar tela
// TODO Implementar acesso ao repository
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFormScreen(
  onNavigateBack: () -> Unit,
  onEventSaved: () -> Unit = {},
  viewModel: EventFormViewModel = hiltViewModel(),
) {
  val showStartTimePicker = remember { mutableStateOf(false) }
  val showEndTimePicker = remember { mutableStateOf(false) }
  val context = LocalContext.current
  val state by viewModel.uiState.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.Success -> {
          Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
          onEventSaved()
        }
        is UiEvent.Error -> {
          Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Novo Evento") },
        navigationIcon = {
          IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
        },
      )
    }
  ) { paddingValues ->
    Column(
      Modifier.padding(paddingValues)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp)
        .padding(top = 16.dp, bottom = 24.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      EventForm(
        descricao = state.description,
        localEvento = state.local,
        regiaoEvento = state.region,
        projetoEvento = state.project,
        grupoEvento = state.groupId,
      )

      // Horários
      Horario(
        onStartTimeClick = { showStartTimePicker.value = !showStartTimePicker.value },
        onEndTimeClick = { showEndTimePicker.value = !showEndTimePicker.value },
        horaInicio = state.hourStart.text.toString(),
        horaFim = state.hourEnd.text.toString(),
      )

      // Botões Salvar e Cancelar
      BotoesDeSalvar(onCancel = { onNavigateBack() }, onSave = { viewModel.onSubmit() })
    }
  }

  when {
    showStartTimePicker.value -> {
      val (hour, minute) = viewModel.getHourAndMinuteFromStateStart()
      TimePickerDialog(
        title = "Horário de inicio",
        hour = hour,
        minute = minute,
        onDismiss = { showStartTimePicker.value = !showStartTimePicker.value },
        updateHourStart = viewModel::updateHourStart,
      )
    }
    showEndTimePicker.value -> {
      val (hour, minute) = viewModel.getHourAndMinuteFromStateEnd()
      TimePickerDialog(
        title = "Horário de Fim",
        hour = hour,
        minute = minute,
        onDismiss = { showEndTimePicker.value = !showEndTimePicker.value },
        updateHourStart = viewModel::updateHourEnd,
      )
    }
  }
}

@Composable
private fun EventForm(
  descricao: TextFieldState,
  localEvento: TextFieldState,
  regiaoEvento: TextFieldState,
  projetoEvento: TextFieldState,
  grupoEvento: TextFieldState,
) {
  // Descrição
  EventField(
    label = "Descrição",
    state = descricao,
    icon = Icons.Default.Title,
    placeholder = "Adicione uma descrição (opcional)",
  )

  // Local do evento
  LocalSelector(selectedLocal = localEvento)

  // Região
  FieldDropdownMenuStyled(
    title = "Região",
    icon = Icons.Default.RealEstateAgent,
    menuOptions = regioesPredefinidas,
    placeholder = "Selecione uma Região",
    fieldState = regiaoEvento,
  )

  // Projeto
  FieldDropdownMenuStyled(
    title = "Projeto",
    icon = Icons.Default.Map,
    menuOptions = projetosPredefinidos,
    placeholder = "Selecione uma Projeto",
    fieldState = projetoEvento,
  )

  // Grupo
  FieldDropdownMenuStyled(
    title = "Grupo",
    icon = Icons.Default.Groups,
    menuOptions = grupos,
    placeholder = "Selecione um Grupo",
    fieldState = grupoEvento,
  )
}

@Composable
private fun BotoesDeSalvar(onCancel: () -> Unit, onSave: () -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(top = 24.dp).padding(horizontal = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    // Botão Cancelar
    OutlinedButton(
      onClick = onCancel,
      modifier = Modifier.weight(1f).height(56.dp),
      shape = RoundedCornerShape(12.dp),
    ) {
      Text(text = "Cancelar", fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }

    // Botão Salvar
    Button(
      onClick = onSave,
      modifier = Modifier.weight(1f).height(56.dp),
      shape = RoundedCornerShape(12.dp),
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(Icons.Default.Check, contentDescription = "Salvar", modifier = Modifier.size(18.dp))
        Text(text = "Salvar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
      }
    }
  }
}

@Composable
private fun Horario(
  onStartTimeClick: () -> Unit,
  horaInicio: String,
  onEndTimeClick: () -> Unit,
  horaFim: String,
) {
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    // Horário de início
    Card(
      modifier = Modifier.weight(1f),
      shape = RoundedCornerShape(12.dp),
      colors =
        CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
    ) {
      Column(modifier = Modifier.fillMaxWidth().clickable { onStartTimeClick() }.padding(16.dp)) {
        Text(
          text = "Horário Início",
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = horaInicio,
          fontSize = 18.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.primary,
        )
      }
    }

    // Horário de fim
    Card(
      modifier = Modifier.weight(1f),
      shape = RoundedCornerShape(12.dp),
      colors =
        CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
    ) {
      Column(modifier = Modifier.fillMaxWidth().clickable { onEndTimeClick() }.padding(16.dp)) {
        Text(
          text = "Horário Fim",
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = horaFim,
          fontSize = 18.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.primary,
        )
      }
    }
  }
}

private fun animateAndSave(
  coroutineScope: CoroutineScope,
  evento: EventDto,
  onEventSaved: (EventDto) -> Unit,
) {
  coroutineScope.launch {
    delay(AppConstants.Animation.defaultDurationMs.toLong())
    onEventSaved(evento)
  }
}

private fun adjustEndTime(startTime: String, currentEndTime: String): String {
  val startParts = startTime.split(":")
  val endParts = currentEndTime.split(":")
  val startMinutes = startParts[0].toInt() * 60 + startParts[1].toInt()
  val endMinutes = endParts[0].toInt() * 60 + endParts[1].toInt()

  return if (endMinutes <= startMinutes) {
    val newEndMinutes = startMinutes + AppConstants.TimeConfig.defaultMeetingDurationMinutes
    val newHour = (newEndMinutes / 60) % 24
    val newMinute = newEndMinutes % 60
    "%02d:%02d".format(newHour, newMinute)
  } else {
    currentEndTime
  }
}

private fun adjustEndTimeIfValid(startTime: String, endTime: String): String {
  val startParts = startTime.split(":")
  val endParts = endTime.split(":")
  val startMinutes = startParts[0].toInt() * 60 + startParts[1].toInt()
  val endMinutes = endParts[0].toInt() * 60 + endParts[1].toInt()

  return if (endMinutes > startMinutes) {
    endTime
  } else {
    val newEndMinutes = startMinutes + AppConstants.TimeConfig.defaultMeetingDurationMinutes
    val newHour = (newEndMinutes / 60) % 24
    val newMinute = newEndMinutes % 60
    "%02d:%02d".format(newHour, newMinute)
  }
}
