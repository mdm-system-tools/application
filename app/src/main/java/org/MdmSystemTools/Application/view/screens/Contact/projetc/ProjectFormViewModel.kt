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

@HiltViewModel
class ProjectFormViewModel
@Inject
constructor(
  private val repository: ProjectRepository,
) : ViewModel() {

  val name: TextFieldState = TextFieldState()
  val region: TextFieldState = TextFieldState()
  val value: TextFieldState = TextFieldState()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun validate(): Boolean {
    return name.text.isNotEmpty() && region.text.isNotEmpty() && value.text.isNotEmpty()
  }

  fun onSubmit() {
    if (!validate()) {
      viewModelScope.launch {
        _uiEvent.emit(UiEvent.Error("Preencha todos os campos"))
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
        _uiEvent.emit(UiEvent.Error("Valor deve ser um número válido"))
      }
      return
    }

    viewModelScope.launch {
      try {
        repository.insert(project)
        _uiEvent.emit(UiEvent.Success("Projeto salvo com sucesso"))
      } catch (e: Exception) {
        _uiEvent.emit(UiEvent.Error("Erro ao salvar: ${e.message}"))
      }
    }
  }

  fun deleteProject(project: Project) {
    viewModelScope.launch {
      try {
        repository.delete(project)
        _uiEvent.emit(UiEvent.Success("Projeto deletado com sucesso"))
      } catch (e: Exception) {
        _uiEvent.emit(UiEvent.Error("Erro ao deletar: ${e.message}"))
      }
    }
  }
}
