package org.MdmSystemTools.Application.view.screens.Contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.view.screens.Contact.ContactUiModel.Associate
import org.MdmSystemTools.Application.view.screens.Contact.ContactUiModel.Group

enum class Tabs(val title: String) {
  ASSOCIATE("Associados"),
  GROUP("Grupos"),
  PROJECT("Projetos"),
  TAREFAS("Tarefas"),
}

sealed class ContactUiModel {
  data class Associate(val data: AssociateDto) : ContactUiModel()

  data class Group(val data: GroupDto) : ContactUiModel()
}

data class ContactUiState(
  val tabSelected: Tabs = Tabs.ASSOCIATE,
  val list: List<ContactUiModel> = emptyList(),
)

@HiltViewModel
class ContactViewModel
@Inject
constructor(
  private val associateRepository: AssociateRepository,
  private val groupRepository: GroupRepository,
) : ViewModel() {
  private val _uiState = MutableStateFlow(ContactUiState())
  val uiState: StateFlow<ContactUiState> = _uiState.asStateFlow()
  private val _listAssociates = MutableStateFlow<List<AssociateDto>>(emptyList())
  val listAssociates: StateFlow<List<AssociateDto>> = _listAssociates.asStateFlow()
  private val _listGroup = MutableStateFlow<List<GroupDto>>(emptyList())
  val listGroup: StateFlow<List<GroupDto>> = _listGroup.asStateFlow()

  init {
    loadDataForTab(_uiState.value.tabSelected)
  }

  fun onTabSelected(tab: Tabs) {
    _uiState.update { it.copy(tabSelected = tab) }
    loadDataForTab(tab)
  }

  private fun loadDataForTab(tab: Tabs) {
    viewModelScope.launch {
      val list =
        when (tab) {
          Tabs.ASSOCIATE -> associateRepository.getAssociates().map { Associate(it) }
          Tabs.GROUP -> groupRepository.getListGroups().map { Group(it) }
          Tabs.PROJECT -> emptyList() // por enquanto
					Tabs.TAREFAS -> emptyList()
				}

      _uiState.update { it.copy(list = list) }
    }
  }
}
