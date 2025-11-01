package org.MdmSystemTools.Application.view.screens.Registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.repository.AssociateRepository


@HiltViewModel
class AssociateListViewModel @Inject constructor(
	private val repository: AssociateRepository
) : ViewModel() {
	private val _listAssociates = MutableStateFlow<List<AssociateDto>>(emptyList())
	val listAssociates: StateFlow<List<AssociateDto>> = _listAssociates.asStateFlow()

	init {
		getListAssociates()
	}

	private fun getListAssociates() {
		viewModelScope.launch {
			try {
				_listAssociates.value = repository.getAssociates()
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	fun getAssociate(id: Int?): AssociateDto {
		return if (id != null)
			_listAssociates.value[id]
		else
			AssociateDto("", 0, 0)
	}

	fun deleteAssociate(id: Int) {
		repository.deleteAssociate(id)
	}
}