package org.MdmSystemTools.Application.view.screens.Contact.projetc

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.repository.ProjectRepository
import org.MdmSystemTools.Application.view.screens.Contact.associate.UiEvent

sealed class ProjectUiEvent {
  data class Success(val message: String) : ProjectUiEvent()
  data class Error(val message: String) : ProjectUiEvent()
}

data class ProjectUiState(
  val projects: List<Project> = emptyList(),
  val isLoading: Boolean = false,
)

@HiltViewModel
class ProjectViewModel
@Inject
constructor(
  private val repository: ProjectRepository,
) : ViewModel() {

  val name: TextFieldState = TextFieldState()
  val region: TextFieldState = TextFieldState()
  val value: TextFieldState = TextFieldState()

  private val _uiEvent = MutableSharedFlow<ProjectUiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  private val _uiState = MutableStateFlow(ProjectUiState())
  val uiState: StateFlow<ProjectUiState> = _uiState.asStateFlow()

  init {
    loadProjects()
  }

  private fun loadProjects() {
    viewModelScope.launch {
      try {
        _uiState.value = _uiState.value.copy(isLoading = true)
        repository.getAll().collectLatest { projects ->
          _uiState.value = _uiState.value.copy(
            projects = projects,
            isLoading = false
          )
        }
      } catch (e: Exception) {
        Log.e("ProjectViewModel", "Erro ao carregar projetos: ${e.message}")
        _uiState.value = _uiState.value.copy(isLoading = false)
      }
    }
  }

  fun validate(): Boolean {
    return name.text.isNotEmpty() && region.text.isNotEmpty() && value.text.isNotEmpty()
  }

  fun onSubmit() {
    if (!validate()) {
      viewModelScope.launch {
        _uiEvent.emit(ProjectUiEvent.Error("Preencha todos os campos"))
      }
      return
    }

    val project = try {
      Project(
        name = name.text.toString(),
        region = region.text.toString(),
        value = value.text.toString().toInt(),
      )
    } catch (e: NumberFormatException) {
      viewModelScope.launch {
        _uiEvent.emit(ProjectUiEvent.Error("Valor deve ser um número"))
      }
      return
    }

    viewModelScope.launch {
      try {
        repository.insert(project)
        _uiEvent.emit(ProjectUiEvent.Success("Projeto salvo com sucesso"))
        clearFields()
        loadProjects()
      } catch (e: Exception) {
        _uiEvent.emit(ProjectUiEvent.Error("Erro ao salvar: ${e.message}"))
      }
    }
  }

  fun deleteProject(project: Project) {
    viewModelScope.launch {
      try {
        repository.delete(project)
        _uiEvent.emit(ProjectUiEvent.Success("Projeto deletado com sucesso"))
        loadProjects()
      } catch (e: Exception) {
        _uiEvent.emit(ProjectUiEvent.Error("Erro ao deletar: ${e.message}"))
      }
    }
  }

  private fun clearFields() {
    name.clearText()
    region.clearText()
    value.clearText()
  }
}
