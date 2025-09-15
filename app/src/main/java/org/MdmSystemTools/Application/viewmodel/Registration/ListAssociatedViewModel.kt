package org.MdmSystemTools.Application.viewmodel.Registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.DTO.AssociatedDto
import org.MdmSystemTools.Application.model.repository.ListAssociatedRepository

@HiltViewModel
class ListAssociatedViewModel @Inject constructor(
  private val repository: ListAssociatedRepository
) : ViewModel() {
  private val _listAssociateds = MutableStateFlow<List<AssociatedDto>>(emptyList())
  val listAssociateds: StateFlow<List<AssociatedDto>> = _listAssociateds.asStateFlow()

  init {
    iniciarList()
  }

  private fun iniciarList() {
    viewModelScope.launch {
      try {
        _listAssociateds.value = repository.getListAssociateds()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}