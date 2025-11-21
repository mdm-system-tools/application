package org.MdmSystemTools.Application.view.screens.Contact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Associate
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.GroupRepository
import org.MdmSystemTools.Application.model.repository.ProjectRepository

enum class TabsForContact(val title: String) {
	ASSOCIATE("Associados"),
	GROUP("Grupos"),
	PROJECT("Projetos"),
}

data class ContactUiState(
	val tabSelected: TabsForContact = TabsForContact.ASSOCIATE,
	val associates: List<Associate> = emptyList(),
	val groups: List<Grupo> = emptyList(),
	val projects: List<Project> = emptyList(),
)

@HiltViewModel
class ContactViewModel
@Inject
constructor(
	private val associateRepository: AssociateRepository,
	private val groupRepository: GroupRepository,
	private val projectRepository: ProjectRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow(ContactUiState())
	val uiState: StateFlow<ContactUiState> = _uiState.asStateFlow()

	init {
		viewModelScope.launch {
			combine(
				associateRepository.getAll(),
				groupRepository.getAll(),
				projectRepository.getAll()
			) { associates, groups, projects ->
				Log.d("ViewModelContact", "Lista de associados $associates")
				Log.d("ViewModelContact", "lista de grupos $groups")
				Log.d("ViewModelContact", "lista de projetos $projects")
				_uiState.update {
					it.copy(
						associates = associates,
						groups = groups,
						projects = projects
					)
				}
			}.collect()
		}
	}
}
