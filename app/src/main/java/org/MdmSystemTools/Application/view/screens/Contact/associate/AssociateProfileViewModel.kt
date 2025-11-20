package org.MdmSystemTools.Application.view.screens.Contact.associate

import android.content.res.Resources
import android.util.Log
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
class AssociateProfileViewModel @Inject constructor(private val repository: AssociateRepository) :
  ViewModel() {
  private val _listAssociates = MutableStateFlow<List<AssociateDto>>(emptyList())
  val listAssociates: StateFlow<List<AssociateDto>> = _listAssociates.asStateFlow()

  init {
    getListAssociates()
  }

  private fun getListAssociates() {
    viewModelScope.launch {
      try {
        _listAssociates.value = repository.getAll()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun getAssociate(id: Int): AssociateDto {
    return try {
      _listAssociates.value[id]
    } catch (e: Resources.NotFoundException) {
      Log.e("ViewModelAssociateList", e.toString())
      AssociateDto("", 0, 0)
    }
  }

  fun deleteAssociate(id: Int) {
    repository.delete(id)
  }
}
