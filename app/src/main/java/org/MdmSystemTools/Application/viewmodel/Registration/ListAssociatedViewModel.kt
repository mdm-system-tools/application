package org.MdmSystemTools.Application.viewmodel.Registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.DTO.AssociateDto
import org.MdmSystemTools.Application.model.repository.ListAssociateRepository

@HiltViewModel
class ListAssociatedViewModel @Inject constructor(
  private val repository: ListAssociateRepository
) : ViewModel() {
  private val _listAssociates = MutableStateFlow<List<AssociateDto>>(emptyList())
  val listAssociates: StateFlow<List<AssociateDto>> = _listAssociates.asStateFlow()

  init {
    getListAssociateds()
  }

  private fun getListAssociateds() {
    viewModelScope.launch {
      try {
        _listAssociates.value = repository.getListAssociates()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}