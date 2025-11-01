package org.MdmSystemTools.Application.view.screens.Calendar

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import org.MdmSystemTools.Application.view.components.EventField
import org.MdmSystemTools.Application.view.components.FieldDropdownMenuStyled
import org.MdmSystemTools.Application.view.components.LocalSelector
import org.MdmSystemTools.Application.view.screens.Meeting.UiEvent
import org.MdmSystemTools.Application.view.theme.AppConstants

@Preview
@Composable
private fun AddEventScreenPreview() {
  EventFormScreen({}, {})
}

private val regioesPredefinidas: List<String> =
  listOf("Norte", "Nordeste", "Centro-Oeste", "Sudeste", "Sul")
private val grupos: List<String> =
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

private val projetosPredefinidos: List<String> =
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
@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFormScreen(
  onNavigateBack: () -> Unit,
  onEventSaved: () -> Unit = {},
  viewModel: EventFormViewModel = hiltViewModel(),
) {
  val showStartTimePicker: MutableState<Boolean> = remember { mutableStateOf(false) }
  val showEndTimePicker: MutableState<Boolean> = remember { mutableStateOf(false) }
  val showDatePicker: MutableState<Boolean> = remember { mutableStateOf(false) }
  val context: Context = LocalContext.current
  val state: EventFormUiState by viewModel.uiState.collectAsState()

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
        descricao = state.title,
        local = state.local,
        regiao = state.region,
        projeto = state.project,
        grupo = state.groupId,
      )

      // Horários
      Horario(
        onStartTimeClick = { showStartTimePicker.value = !showStartTimePicker.value },
        onEndTimeClick = { showEndTimePicker.value = !showEndTimePicker.value },
        onDateClick = { showDatePicker.value = !showDatePicker.value },
        horaInicio = String.format("%02d:%02d", state.startTime.hour, state.startTime.minute),
        horaFim = String.format("%02d:%02d", state.endTime.hour, state.endTime.minute),
        date = viewModel.formatDate(state.date.selectedDateMillis),
      )

      // Botões Salvar e Cancelar
      BotoesDeSalvar(onCancel = { onNavigateBack() }, onSave = { viewModel.onSubmit() })
    }
  }

  when {
    showStartTimePicker.value -> {
      TimePickerDialog(
        onDismissRequest = { showStartTimePicker.value = false },
        confirmButton = {
          TextButton(onClick = { showStartTimePicker.value = false }) { Text("Confirmar") }
        },
        title = { Text("Alterar Horas") },
      ) {
        TimePicker(state = state.startTime)
      }
    }

    showEndTimePicker.value -> {
      TimePickerDialog(
        onDismissRequest = { showEndTimePicker.value = false },
        confirmButton = {
          TextButton(onClick = { showEndTimePicker.value = false }) { Text("Confirmar") }
        },
        title = { Text("Alterar Horas") },
      ) {
        TimePicker(state = state.endTime)
      }
    }

    showDatePicker.value -> {
      DatePickerDialog(
        onDismissRequest = { showDatePicker.value = false },
        confirmButton = { TextButton(onClick = { showDatePicker.value = false }) { Text("OK") } },
        dismissButton = {
          TextButton(onClick = { showDatePicker.value = false }) { Text("cancelar") }
        },
      ) {
        DatePicker(state = state.date)
      }
    }
  }
}

@Composable
private fun EventForm(
  descricao: TextFieldState,
  local: TextFieldState,
  regiao: TextFieldState,
  projeto: TextFieldState,
  grupo: TextFieldState,
) {
  // Descrição
  EventField(
    label = "Descrição",
    state = descricao,
    icon = Icons.Default.Title,
    placeholder = "Adicione uma descrição (opcional)",
  )

  // Local do evento
  LocalSelector(selectedLocal = local)

  // Região
  FieldDropdownMenuStyled(
    title = "Região",
    icon = Icons.Default.RealEstateAgent,
    menuOptions = regioesPredefinidas,
    placeholder = "Selecione uma Região",
    fieldState = regiao,
  )

  // Projeto
  FieldDropdownMenuStyled(
    title = "Projeto",
    icon = Icons.Default.Map,
    menuOptions = projetosPredefinidos,
    placeholder = "Selecione uma Projeto",
    fieldState = projeto,
  )

  // Grupo
  FieldDropdownMenuStyled(
    title = "Grupo",
    icon = Icons.Default.Groups,
    menuOptions = grupos,
    placeholder = "Selecione um Grupo",
    fieldState = grupo,
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
  onEndTimeClick: () -> Unit,
  onDateClick: () -> Unit,
  horaInicio: String,
  horaFim: String,
  date: String,
) {
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    CardHours(
      title = "Horário Início",
      description = horaInicio,
      onClick = onStartTimeClick,
      Modifier.weight(1f),
    )
    CardHours(
      title = "Horário Fim",
      description = horaFim,
      onClick = onEndTimeClick,
      Modifier.weight(1f),
    )
    CardHours(title = "Data", description = date, onClick = onDateClick, Modifier.weight(1f))
  }
}

@Composable
private fun CardHours(title: String, description: String, onClick: () -> Unit, modifier: Modifier) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors =
      CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
      ),
  ) {
    Column(modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp)) {
      Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = description,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
      )
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
