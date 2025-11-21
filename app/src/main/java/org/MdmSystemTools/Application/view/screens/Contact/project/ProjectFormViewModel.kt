package org.MdmSystemTools.Application.view.screens.Contact.project

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.repository.ProjectRepository
import org.MdmSystemTools.Application.view.components.UiEvent

data class ProjectFormUiState(
	val name: TextFieldState = TextFieldState(),
	val region: TextFieldState = TextFieldState(),
	val value: TextFieldState = TextFieldState()
)

@HiltViewModel
class ProjectFormViewModel
@Inject
constructor(
	private val repository: ProjectRepository,
) : ViewModel() {

	private val _uiEvent = MutableSharedFlow<UiEvent>()
	val uiEvent = _uiEvent.asSharedFlow()
	private val _uiState = MutableStateFlow(ProjectFormUiState())
	val uiState = _uiState.asStateFlow()

	fun validate(state: ProjectFormUiState): Boolean {
		if (state.name.text.isNotEmpty() && state.region.text.isNotEmpty() && state.value.text.isNotEmpty())
			return true
		else {
			viewModelScope.launch {
				_uiEvent.emit(UiEvent.Error("Preencha todos os campos"))
			}
			return false
		}
	}

	fun onSubmit(state: ProjectFormUiState) {
		val project = try {
			Project(
				name = state.name.text.toString(),
				region = state.region.text.toString(),
				value = state.value.text.toString().toLong(),
				completed = false
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
