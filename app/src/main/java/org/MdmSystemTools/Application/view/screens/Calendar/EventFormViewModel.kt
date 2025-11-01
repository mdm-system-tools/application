package org.MdmSystemTools.Application.view.screens.Calendar

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.io.IOException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.EventDate
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.model.repository.EventRepository
import org.MdmSystemTools.Application.view.screens.Meeting.UiEvent

data class EventFormUiState(
  val title: TextFieldState = TextFieldState(),
  val description: TextFieldState = TextFieldState(),
  val day: TextFieldState = TextFieldState(),
  val month: TextFieldState = TextFieldState(),
  val year: TextFieldState = TextFieldState(),
  val hourStart: TextFieldState = TextFieldState(),
  val hourEnd: TextFieldState = TextFieldState(),
  val local: TextFieldState = TextFieldState(),
  val region: TextFieldState = TextFieldState(),
  val project: TextFieldState = TextFieldState(),
  val groupId: TextFieldState = TextFieldState(),
)

@HiltViewModel
class EventFormViewModel @Inject constructor(private val repository: EventRepository) :
  ViewModel() {

  private val _uiState = MutableStateFlow(EventFormUiState())
  val uiState = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun onFieldChange(field: (EventFormUiState) -> EventFormUiState) {
    _uiState.value = field(_uiState.value)
    validate()
  }

  private fun validate(): Boolean {
    val s = _uiState.value
    return s.title.text.isNotBlank() &&
      s.description.text.isNotBlank() &&
      s.day.text.isNotBlank() &&
      s.month.text.isNotBlank() &&
      s.year.text.isNotBlank()
  }

  fun updateHourStart(hour: Int, minute: Int) {
    val formatted = "%02d:%02d".format(hour, minute) // 08:30, 14:05 etc
    uiState.value.hourStart.edit {
      replace(0, length, formatted) // substitui o texto atual
      placeCursorAtEnd()
    }
  }

  fun updateHourEnd(hour: Int, minute: Int) {
    val formatted = "%02d:%02d".format(hour, minute) // 08:30, 14:05 etc
    uiState.value.hourEnd.edit {
      replace(0, length, formatted) // substitui o texto atual
      placeCursorAtEnd()
    }
  }

  fun getHourAndMinuteFromStateStart(): Pair<Int, Int> {
    val timeText = uiState.value.hourStart.text.toString()
    return try {
      val (hour, minute) = timeText.split(":").map { it.toInt() }
      hour to minute
    } catch (e: Exception) {
      0 to 0 // valor padrão caso o campo esteja vazio
    }
  }

  fun getHourAndMinuteFromStateEnd(): Pair<Int, Int> {
    val timeText = uiState.value.hourStart.text.toString()
    return try {
      val (hour, minute) = timeText.split(":").map { it.toInt() }
      hour to minute
    } catch (e: Exception) {
      0 to 0 // valor padrão caso o campo esteja vazio
    }
  }

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
          title = state.title.toString(),
          description = state.description.toString(),
          date =
            EventDate(
              state.day.toString().toInt(),
              state.month.toString().toInt(),
              state.year.toString().toInt(),
            ),
          hourStart = state.hourStart.toString(),
          hourEnd = state.hourEnd.toString(),
          local = state.local.toString(),
          region = state.region.toString(),
          project = state.project.toString(),
          groups =
            GroupDto(state.groupId.toString(), "Grupo ${state.groupId}", color = Color(0xFF1C6AEA)),
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
