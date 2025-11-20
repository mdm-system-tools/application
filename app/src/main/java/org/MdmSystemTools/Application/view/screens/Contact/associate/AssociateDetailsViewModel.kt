package org.MdmSystemTools.Application.view.screens.Contact.associate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import org.MdmSystemTools.Application.model.repository.AssociateRepository


data class AssociateDetailsUiState(
	val name: String = "",
	val numberCard: String = "",
	val groupId: Int = 0
)

@HiltViewModel
class AssociateDetailsViewModel @Inject constructor(private val repository: AssociateRepository, savedStateHandle: SavedStateHandle) :
	ViewModel() {

	private val associateId: Int = savedStateHandle["id"]!!
//	private val _associate = repository.getById(associateId)
//
//
//	val uiState: StateFlow<AssociateDetailsUiState> = combine( )


}
