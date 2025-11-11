package org.MdmSystemTools.Application.view.screens.Contact.group

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.io.IOException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.model.repository.ProjectRepository
import org.MdmSystemTools.Application.view.screens.Contact.associate.UiEvent

@HiltViewModel
class GroupFormViewModel
@Inject
constructor(
  private val repository: GroupRepository,
  private val repositoryProject: ProjectRepository,
) : ViewModel() {
  val schedule: TextFieldState = TextFieldState()
  val groupId: TextFieldState = TextFieldState()
  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()
  private val _projectId = MutableStateFlow<Int?>(null)
  val projectId: StateFlow<Int?> = _projectId

  init {
    viewModelScope.launch {
      try {
        val id =
          repositoryProject
            .insert(Project(name = "Projeto A", region = "Sul", value = 5000))
            .toInt()
        _projectId.value = id
      } catch (e: Exception) {
        Log.e("ViewmodelGroupForm", e.toString())
      }
    }
  }

  private fun addGroup(group: Grupo) {
    viewModelScope.launch { repository.insert(group) }
  }

  fun deleteGroup(group: Grupo) {
    viewModelScope.launch { repository.delete(group) }
  }

  fun validate(): Boolean {
    return schedule.text.isNotEmpty() && groupId.text.isNotEmpty() && _projectId.value != null
  }

  fun onSubmit() {
    if (!validate()) {
      viewModelScope.launch { _uiEvent.emit(UiEvent.Error("Preencha todos os campos")) }
      return
    }
    val group =
      try {
        // TODO isso só foi colocado para parar de dar erro
        Grupo(projectId = _projectId.value?.toInt() ?: 0, schedule = schedule.text.toString())
      } catch (e: IOException) {
        viewModelScope.launch {
          _uiEvent.emit(UiEvent.Error("Erro ao criar associado: ${e.message}"))
        }
        return
      }
    viewModelScope.launch {
      try {
        repository.insert(group)
        _uiEvent.emit(UiEvent.Success("grupo salvo com sucesso"))
      } catch (e: Exception) {
        _uiEvent.emit(UiEvent.Error("Erro ao salvar: ${e.message}"))
      }
    }
  }
}
