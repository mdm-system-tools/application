package org.MdmSystemTools.Application.view.screens.Contact.group

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.view.screens.Contact.associate.UiEvent
import java.io.IOException

@HiltViewModel
class GroupFormViewModel @Inject constructor(private val repository: GroupRepository) :
  ViewModel() {
  val schedule: TextFieldState = TextFieldState()
  val groupId: TextFieldState = TextFieldState()
  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  private fun addGroup(group: Grupo) {
    viewModelScope.launch {
      repository.addGroup(group)
    }
  }

  fun deleteGroup(group: Grupo) {
    viewModelScope.launch {
      repository.deleteGroup(group)
    }
  }

  fun validate(): Boolean {
    return schedule.text.isNotEmpty() && groupId.text.isNotEmpty()
  }

  fun onSubmit() {
    if (!validate()) {
      viewModelScope.launch { _uiEvent.emit(UiEvent.Error("Preencha todos os campos")) }
      return
    }
    val group =
      try {
				//TODO isso só foi colocado para parar de dar erro
        Grupo(projectId = 1, schedule = schedule.text.toString())
      } catch (e: IOException) {
        viewModelScope.launch {
          _uiEvent.emit(UiEvent.Error("Erro ao criar associado: ${e.message}"))
        }
        return
      }
		viewModelScope.launch {
			try {
				repository.addGroup(group)
				_uiEvent.emit(UiEvent.Success("grupo salvo com sucesso"))
			} catch (e: Exception) {
				_uiEvent.emit(UiEvent.Error("Erro ao salvar: ${e.message}"))
			}
		}
  }
}
