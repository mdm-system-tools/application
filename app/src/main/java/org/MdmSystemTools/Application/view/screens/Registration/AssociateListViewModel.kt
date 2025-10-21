package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.foundation.text.input.TextFieldState
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
	val name: TextFieldState = TextFieldState()
	val numberCard: TextFieldState = TextFieldState()
	val groupId: TextFieldState = TextFieldState()

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

	fun validate(): Boolean {
		return name.text.toString().isNotBlank() &&
			numberCard.text.toString().toInt() != 0 &&
			groupId.text.toString().toInt() != 0
	}

	fun onSubmit() {
		val assoc = AssociateDto(
			name.text.toString(),
			numberCard.text.toString().toInt(),
			groupId.text.toString().toInt()
		)
		if (validate()) {
			repository.addAssociate(assoc)
		} else throw Exception("algum valor esta nulo")
	}
}