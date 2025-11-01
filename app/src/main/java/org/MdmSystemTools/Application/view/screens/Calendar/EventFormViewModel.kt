package org.MdmSystemTools.Application.view.screens.Calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.repository.EventRepository
import org.MdmSystemTools.Application.view.screens.Meeting.UiEvent

data class EventFormUiState
@OptIn(ExperimentalMaterial3Api::class)
constructor(
  val title: TextFieldState = TextFieldState(),
  val date: DatePickerState =
    DatePickerState(
      initialSelectedDateMillis = System.currentTimeMillis(),
      yearRange = 1900..2100,
      locale = Locale.getDefault(),
    ),
  val startTime: TimePickerState =
    TimePickerState(
      initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
      initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
      is24Hour = true,
    ),
  val endTime: TimePickerState =
    TimePickerState(
      initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
      initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
      is24Hour = true,
    ),
  val local: TextFieldState = TextFieldState(),
  val region: TextFieldState = TextFieldState(),
  val project: TextFieldState = TextFieldState(),
  val groupId: TextFieldState = TextFieldState(),
)

@HiltViewModel
class EventFormViewModel @Inject constructor(private val repository: EventRepository) :
  ViewModel() {

  @OptIn(ExperimentalMaterial3Api::class)
  private val _uiState = MutableStateFlow(EventFormUiState())
  val uiState = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  private fun validate(): Boolean {
    val s = _uiState.value
    return s.title.text.isNotBlank()
  }

  @Composable
  fun formatDate(millis: Long?): String {
    if (millis == null) return "Selecione uma data"
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
  }

  @SuppressLint("DefaultLocale")
  @OptIn(ExperimentalMaterial3Api::class)
  fun onSubmit() {
    if (!validate()) {
      viewModelScope.launch {
        _uiEvent.emit(UiEvent.Error("Preencha todos os campos obrigatórios"))
      }
      return
    }

    val event =
      try {
        val state = _uiState.value
        EventDto(
          title = state.title.text.toString(),
          date =
            state.date.selectedDateMillis
              ?: System
                .currentTimeMillis(), // TODO seria melhor tratar isso antes de tentar enviar para o
          // DTO
          hourStart = String.format("%02d:%02d", state.startTime.hour, state.startTime.minute),
          hourEnd = String.format("%02d:%02d", state.endTime.hour, state.endTime.minute),
        )
      } catch (e: IOException) {
        viewModelScope.launch {
          _uiEvent.emit(UiEvent.Error("Erro ao criar associado: ${e.message}"))
        }
        return
      }

    viewModelScope.launch {
      try {
        repository.addEvent(event)
        _uiEvent.emit(UiEvent.Success("Evento criado com sucesso!"))
      } catch (e: Exception) {
        _uiEvent.emit(UiEvent.Error("Erro: ${e.message}"))
      }
    }
  }
}
