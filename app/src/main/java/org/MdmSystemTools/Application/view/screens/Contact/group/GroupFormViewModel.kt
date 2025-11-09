package org.MdmSystemTools.Application.view.screens.Contact.group

import androidx.lifecycle.ViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.model.repository.GroupRepository

class GroupFormViewModel @Inject constructor(private val repository: GroupRepository) :
  ViewModel() {
	private val _groups = MutableStateFlow<List<GroupDto>>(emptyList())
  val groups: StateFlow<List<GroupDto>> = _groups.asStateFlow()

  fun addGroup(group: GroupDto) {
    repository.addGroup(group)
  }
}
