package org.MdmSystemTools.Application.view.screens.Contact.projetc

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.repository.ProjectRepository
import org.MdmSystemTools.Application.view.screens.Contact.associate.UiEvent

sealed class ProjectFormUiEvent {
  data class Success(val message: String) : ProjectFormUiEvent()
  data class Error(val message: String) : ProjectFormUiEvent()
}

@HiltViewModel
class ProjectFormViewModel
@Inject
constructor(
  private val repository: ProjectRepository,
) : ViewModel() {

  val name: TextFieldState = TextFieldState()
  val region: TextFieldState = TextFieldState()
  val value: TextFieldState = TextFieldState()

  private val _uiEvent = MutableSharedFlow<ProjectFormUiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun validate(): Boolean {
    return name.text.isNotEmpty() && region.text.isNotEmpty() && value.text.isNotEmpty()
  }

  fun onSubmit() {
    if (!validate()) {
      viewModelScope.launch {
        _uiEvent.emit(ProjectFormUiEvent.Error("Preencha todos os campos"))
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
        _uiEvent.emit(ProjectFormUiEvent.Error("Valor deve ser um número válido"))
      }
      return
    }

    viewModelScope.launch {
      try {
        repository.insert(project)
        clearFields()
        _uiEvent.emit(ProjectFormUiEvent.Success("Projeto salvo com sucesso"))
      } catch (e: Exception) {
        _uiEvent.emit(ProjectFormUiEvent.Error("Erro ao salvar: ${e.message}"))
      }
    }
  }

  private fun clearFields() {
    name.edit { replace(0, length, "") }
    region.edit { replace(0, length, "") }
    value.edit { replace(0, length, "") }
  }

  fun deleteProject(project: Project) {
    viewModelScope.launch {
      try {
        repository.delete(project)
        _uiEvent.emit(ProjectFormUiEvent.Success("Projeto deletado com sucesso"))
      } catch (e: Exception) {
        _uiEvent.emit(ProjectFormUiEvent.Error("Erro ao deletar: ${e.message}"))
      }
    }
  }
}
