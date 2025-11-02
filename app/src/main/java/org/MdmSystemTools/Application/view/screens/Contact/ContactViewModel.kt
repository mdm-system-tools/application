package org.MdmSystemTools.Application.view.screens.Contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.GroupRepository

@HiltViewModel
class ContactViewModel
@Inject
constructor(
  private val repository: AssociateRepository,
  private val groupRepository: GroupRepository,
) : ViewModel() {
  private val _listAssociates = MutableStateFlow<List<AssociateDto>>(emptyList())
  val listAssociates: StateFlow<List<AssociateDto>> = _listAssociates.asStateFlow()

  private val _listGroup = MutableStateFlow<List<GroupDto>>(emptyList())
  val listGroup: StateFlow<List<GroupDto>> = _listGroup.asStateFlow()

  init {
    getListAssociates()
    getListGroup()
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

  private fun getListGroup() {
    viewModelScope.launch {
      try {
        _listGroup.value = groupRepository.getListGroups()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}
