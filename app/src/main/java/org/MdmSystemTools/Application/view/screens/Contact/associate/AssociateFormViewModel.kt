package org.MdmSystemTools.Application.view.screens.Contact.associate

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Associate
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import java.io.IOException


data class AssociateFormUiState(
	val name: TextFieldState = TextFieldState(),
	val numberCard: TextFieldState = TextFieldState(),
	val groupId: TextFieldState = TextFieldState()
)

@HiltViewModel
class AssociateFormViewModel @Inject constructor(private val repository: AssociateRepository) :
	ViewModel() {
	private val _uiState = MutableStateFlow(AssociateFormUiState())
	val uiState: StateFlow<AssociateFormUiState> = _uiState.asStateFlow()

	fun isFormValid(state: AssociateFormUiState): Boolean {
		return state.name.text.isNotBlank() &&
			state.numberCard.text.isNotBlank() &&
			state.groupId.text.isNotBlank()
	}

	fun save(state: AssociateFormUiState) {
		val associate: Associate = try {
			Associate(
				state.numberCard.text.toString(),
				state.name.text.toString(),
				state.groupId.text.toString().toInt()
			)
		} catch (e: IOException) {
			Log.e("ViewModelAssociateForm", e.toString())
			return
		}
		viewModelScope.launch {
			repository.insert(associate)
		}
	}
}
