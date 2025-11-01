package org.MdmSystemTools.Application.view.screens.Calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.repository.EventRepository

@HiltViewModel
class EventListViewModel @Inject constructor(private val repository: EventRepository) :
  ViewModel() {
  private val _listEvents = MutableStateFlow<List<EventDto>>(emptyList())
  val listEvents: StateFlow<List<EventDto>> = _listEvents.asStateFlow()

  init {
    getListEvents()
  }

  private fun getListEvents() {
    viewModelScope.launch {
      try {
        _listEvents.value = repository.getAllEvents()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun getEvent(id: String): EventDto? {
    return _listEvents.value.find { it.id == id }
  }

  fun deleteEvent(id: Int) {
    repository.removeEvent(id)
  }
}
