package org.MdmSystemTools.Application.view.screens.Contact.associate

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Associate
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.view.components.DropdownOption
import org.MdmSystemTools.Application.view.components.UiEvent
import java.io.IOException


data class AssociateFormUiState(
	val name: TextFieldState = TextFieldState(),
	val numberCard: TextFieldState = TextFieldState(),
	val groupId: TextFieldState = TextFieldState()
)

@HiltViewModel
class AssociateFormViewModel @Inject constructor(
	private val repository: AssociateRepository,
	private val repositoryGroup: GroupRepository
) :
	ViewModel() {
	private val _uiState = MutableStateFlow(AssociateFormUiState())
	val uiState: StateFlow<AssociateFormUiState> = _uiState.asStateFlow()
	private val _uiEvent = MutableSharedFlow<UiEvent>()
	val uiEvent = _uiEvent.asSharedFlow()

	val groupsOptions: StateFlow<List<DropdownOption>> = repositoryGroup.getAll().map { list ->
		list.map { DropdownOption(it.id, it.schedule) }
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000),
		initialValue = emptyList()
	)

	fun isFormValid(state: AssociateFormUiState): Boolean {
		if (state.name.text.isNotBlank() && state.numberCard.text.isNotBlank()
			&& state.groupId.text.isNotBlank()
		) return true
		else {
			viewModelScope.launch { _uiEvent.emit(UiEvent.Error("Preencha todos os campos")) }
			return false
		}
	}

	suspend fun getGroupById(id: Long): Grupo {
		try {
			return repositoryGroup.getById(id)
		} catch (e: Exception) {
			_uiEvent.emit(UiEvent.Error("ID do grupo inválido: ${e.message}"))
		}

		return Grupo(-1, -1, "")
	}

	fun save(state: AssociateFormUiState) {
		val groupId = state.groupId.text.toString().toLong()
		viewModelScope.launch {
			val group = getGroupById(groupId)
			val associate: Associate

			try {
				if (group.id == -1L) {
					_uiEvent.emit(UiEvent.Error("Grupo não encontrando"))
					return@launch
				}
				associate = Associate(
					numberCard = state.numberCard.text.toString(),
					name = state.name.text.toString(),
					groupId = group.id
				)
			} catch (e: IOException) {
				_uiEvent.emit(UiEvent.Error("Erro ao criar associado: ${e.message}"))
				return@launch
			}

			try {
				repository.insert(associate)
				_uiEvent.emit(UiEvent.Success("Associado salvo com sucesso"))
			} catch (e: Exception) {
				_uiEvent.emit(UiEvent.Error("Erro ao salvar: ${e.message}"))
			}
		}
	}
}
