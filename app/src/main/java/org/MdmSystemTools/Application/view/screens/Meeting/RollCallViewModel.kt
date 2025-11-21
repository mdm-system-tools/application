package org.MdmSystemTools.Application.view.screens.Meeting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.MdmSystemTools.Application.model.repository.AssociateRepository

enum class TabsForRollCall(val title: String, val index: Int) {
	ROLLCALL("Chamada", 0),
	HISTORY("Histórico", 1)
}

data class RollCallUiState(
	var targetPage: Int = 0
)

@HiltViewModel
class RollCallViewModel @Inject constructor(private val repository: AssociateRepository) :
	ViewModel() {
	private val _uiState = MutableStateFlow(RollCallUiState())
	val uiState: StateFlow<RollCallUiState> = _uiState.asStateFlow()

	fun changePage(index: Int) {
		_uiState.update { it.copy(targetPage = index) }
	}
}
