package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.repository.ListAssociateRepository


@HiltViewModel
class AssociateListViewModel @Inject constructor(
	private val repository: ListAssociateRepository
) : ViewModel() {
	private val _listAssociates = MutableStateFlow<List<AssociateDto>>(emptyList())
	val listAssociates: StateFlow<List<AssociateDto>> = _listAssociates.asStateFlow()

	var formState by mutableStateOf(AssociateDto())
		private set

	init {
		getListAssociates()
	}

	private fun getListAssociates() {
		viewModelScope.launch {
			try {
				_listAssociates.value = repository.getListAssociates()
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	fun onNameChange(newName: String) {
		formState = formState.copy(name = newName)
		validate()
	}

	fun onNumberCardChange(newNumberCard: Int) {
		formState = formState.copy(numberCard = newNumberCard)
		validate()
	}

	fun onGroupChange(newGroup: Int) {
		formState = formState.copy(groupId = newGroup)
		validate()
	}

	private fun validate() {
		val valid = formState.name.isNotBlank() &&
			formState.groupId != 0 && formState.numberCard != 0

		formState = formState.copy(
			//TODO essa abordagem adicionou um novo atributo a estrutuda de dados
			//não sei se está correto essa abordagem
			isValid = valid,
			errorMessage = if (!valid) "Preencha todos os campos corretamente" else null
		)
	}

	fun onSubmit(name: String, numberCard: String, groupId: String) {
		val assoc = AssociateDto(name, numberCard.toInt(), groupId.toInt())
		repository.addAssociate(assoc)
		if (formState.isValid) {
			//repository.addAssociate(assoc)
		}
	}
}