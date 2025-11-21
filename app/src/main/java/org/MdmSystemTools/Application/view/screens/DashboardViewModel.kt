package org.MdmSystemTools.Application.view.screens

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
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
import org.MdmSystemTools.Application.model.repository.AssociateRepository
import org.MdmSystemTools.Application.model.repository.MeetingRepository
import org.MdmSystemTools.Application.model.repository.ProjectRepository
import java.util.Calendar
import java.util.Locale

data class DashboardUiState @OptIn(ExperimentalMaterial3Api::class) constructor(
	val amountAssociate: Int = 0,
	val amountCompleted: Int = 0,
	val amountProject: Int = 0,
	val amountMeeting: Int = 0,
	val project: TextFieldState = TextFieldState(),
	val date: DatePickerState = DatePickerState(
		initialSelectedDateMillis = System.currentTimeMillis(),
		yearRange = 1900..2100,
		locale = Locale.getDefault(),
	),
	val time: TimePickerState = TimePickerState(
		initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
		initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
		is24Hour = true,
	),
	val local: TextFieldState = TextFieldState(),
	val address: TextFieldState = TextFieldState(),
	val showDialog: Boolean = false,
	val showTimePicker: Boolean = false,
)

@HiltViewModel
@OptIn(ExperimentalMaterial3Api::class)
class DashboardViewModel @Inject constructor(
	private val meetingRepository: MeetingRepository,
	private val associateRepository: AssociateRepository,
	private val projectRepository: ProjectRepository
) :
	ViewModel() {
	fun closeDialog() {
		_uiState.update { it.copy(showDialog = false) }
	}

	fun openDialog() {
		_uiState.update { it.copy(showDialog = true) }
	}

	fun closeTimePicker() {
		_uiState.update { it.copy(showTimePicker = false) }
	}

	fun openTimePicker() {
		_uiState.update { it.copy(showTimePicker = true) }
	}

	private val _uiState = MutableStateFlow(DashboardUiState())
	val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()


	init {
		viewModelScope.launch {
			combine(
				projectRepository.count(),
				associateRepository.count(),
				projectRepository.countCompleted()
			) { projects, associates, completed ->
				Log.d("ViewModelDashboard", "Quantidade de associados encontrando: $associates")
				Log.d("ViewModelDashboard", "Quantidade de projetos encontrando: $projects")
				Log.d("ViewModelDashboard", "Quantidade de projetos concluido encontrando: $completed")
				_uiState.update {
					it.copy(
						amountAssociate = associates,
						amountProject = projects,
						amountCompleted = completed
					)
				}
			}.collect()
		}
	}

}
