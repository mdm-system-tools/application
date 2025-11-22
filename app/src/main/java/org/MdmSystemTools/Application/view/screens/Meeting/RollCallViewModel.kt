package org.MdmSystemTools.Application.view.screens.Meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Meeting
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.entity.ProjectWithGroups
import org.MdmSystemTools.Application.model.repository.MeetingRepository
import org.MdmSystemTools.Application.model.repository.MeetingWithProjectRepository
import org.MdmSystemTools.Application.model.repository.ProjectRepository
import org.MdmSystemTools.Application.model.repository.ProjectWithGroupsRepository

enum class TabsForRollCall(val title: String, val index: Int) {
	ROLLCALL("Chamada", 0),
	HISTORY("Histórico", 1)
}

data class RollCallUiState(
	var targetPage: Int = 0,
	val projectsWithGroups: List<ProjectWithGroups> = emptyList(),
	val meetingsWithDetails: List<MeetingWithDetails> = emptyList(),
)

data class MeetingWithDetails(
	val meetingId: Long,
	val meetingTitle: String,
	val meetingDateTime: Long,        // timestamp
	val projectName: String,
	val groups: List<String>          // só os nomes dos grupos
)

@HiltViewModel
class RollCallViewModel @Inject constructor(
	private val projectWithGroupsRepository: ProjectWithGroupsRepository,
	private val meetingWithProjectRepository: MeetingWithProjectRepository,
	private val meetingRepository: MeetingRepository,
	private val projectRepository: ProjectRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow(RollCallUiState())
	val uiState: StateFlow<RollCallUiState> = _uiState.asStateFlow()

	fun changePage(index: Int) {
		_uiState.update { it.copy(targetPage = index) }
	}

//	init {
//		viewModelScope.launch {
//			combine(
//				projectWithGroupsRepository.getAll(),
//				meetingWithProjectRepository.getAll(),
//			){ projectWithGroups, meetingWithProject ->
//				_uiState.update {
//					it.copy(
//						projectsWithGroups = projectWithGroups,
//						meetingWithProjects = meetingWithProject
//					)
//				}
//			}.collect()
//		}
//	}
	init {
		viewModelScope.launch {
			projectRepository.insert(Project(region = "lala", name = "lalala", value = 10L, completed = false))
			meetingRepository.insert(Meeting(projectId = 1, date = 0L, address = ""))
			// 1. Pega todas as reuniões
			meetingWithProjectRepository.getAll()
				.collect { meetings ->

					// 2. Para cada reunião, busca o projeto + grupos
					val meetingsWithDetails = meetings.map { meeting ->
						val projectWithGroups = projectWithGroupsRepository.getProjectWithGroups(meeting.project.id)  // ← aqui você usa o getById

						MeetingWithDetails(
							meetingId = meeting.meeting.id,
							meetingTitle = meeting.meeting.address,
							meetingDateTime = meeting.meeting.date,
							projectName = projectWithGroups.project.name,
							groups = projectWithGroups.groups.map { it.schedule }
						)
					}

					// 3. Atualiza o UI com a lista pronta
					_uiState.update {
						it.copy(meetingsWithDetails = meetingsWithDetails)
					}
				}
		}
	}
}
