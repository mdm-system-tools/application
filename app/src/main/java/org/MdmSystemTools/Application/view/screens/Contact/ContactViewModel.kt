package org.MdmSystemTools.Application.view.screens.Contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.view.screens.Contact.ContactUiModel.Associate
import org.MdmSystemTools.Application.view.screens.Contact.ContactUiModel.Group

enum class Tabs(val title: String) {
  ASSOCIATE("Associados"),
  GROUP("Grupos"),
  PROJECT("Projetos"),
}

sealed class ContactUiModel {
  data class Associate(val data: AssociateDto) : ContactUiModel()

  data class Group(val data: Grupo) : ContactUiModel()
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
  private val _listGroup = MutableStateFlow<List<Grupo>>(emptyList())
  val listGroup: StateFlow<List<Grupo>> = _listGroup.asStateFlow()

  init {
    loadDataForTab(_uiState.value.tabSelected)
  }

  fun onTabSelected(tab: Tabs) {
    viewModelScope.launch {
      _uiState.update { it.copy(tabSelected = tab) }
      loadDataForTab(tab)
    }
  }

  private fun loadDataForTab(tab: Tabs) {
    viewModelScope.launch {
      when (tab) {
        Tabs.ASSOCIATE -> {
          val associates = associateRepository.getAll().map { Associate(it) }
          _uiState.update { it.copy(list = associates) }
        }
        Tabs.GROUP -> {
          groupRepository.getAll().collectLatest { groups ->
            _uiState.update { it.copy(list = groups.map { Group(it) }) }
          }
        }

        else -> {
          _uiState.update { it.copy(list = emptyList()) }
        }
      }
    }
  }
}
