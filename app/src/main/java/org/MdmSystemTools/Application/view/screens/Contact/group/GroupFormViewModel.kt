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
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.model.repository.ProjectRepository
import java.io.IOException

sealed class UiEvent {
	data class Success(val message: String) : UiEvent()
	data class Error(val message: String) : UiEvent()
}

data class GroupFormUiState(
	val schedule: TextFieldState = TextFieldState(),
	val projectId: TextFieldState = TextFieldState(),
	val errorMessage: String? = null,
	val success: Boolean = false
)

@HiltViewModel
class GroupFormViewModel @Inject constructor(
	private val repository: GroupRepository,
	private val repositoryProject: ProjectRepository
) : ViewModel() {

	private val _uiState = MutableStateFlow(GroupFormUiState())
	val uiState: StateFlow<GroupFormUiState> = _uiState.asStateFlow()
	private val _uiEvent = MutableSharedFlow<UiEvent>()
	val uiEvent = _uiEvent.asSharedFlow()

	suspend fun getProjectId(id: Int): Project {
		try {
			return repositoryProject.getById(id)
		} catch (e: Exception) {
			_uiEvent.emit(UiEvent.Error("ID do projeto inválido: ${e.message}"))
		}

		return Project(0, "", "", 0)
	}

	fun isFormValid(state: GroupFormUiState): Boolean {
		return if (state.schedule.text.isNotBlank() && state.projectId.text.isNotBlank())
			true
		else {
			viewModelScope.launch { _uiEvent.emit(UiEvent.Error("Preencha todos os campos")) }
			false
		}
	}

	fun save(state: GroupFormUiState) {
		if (!isFormValid(state)) return

		val projectId = state.projectId.text.toString().toInt()


		viewModelScope.launch {
			val project = getProjectId(projectId)

			val group: Grupo
			try {
				group = Grupo(
					schedule = state.schedule.text.toString(),
					projectId = project.id
				)
			} catch (e: IOException) {
				_uiEvent.emit(UiEvent.Error("Erro ao criar associado: ${e.message}"))
				return@launch // interrompe a execução se falhar
			}

			try {
				repository.insert(group)
				_uiEvent.emit(UiEvent.Success("Grupo salvo com sucesso"))
			} catch (e: Exception) {
				_uiEvent.emit(UiEvent.Error("Erro ao salvar: ${e.message}"))
			}
		}

	}
}
