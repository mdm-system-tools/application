package org.MdmSystemTools.Application.view.screens.Calendar

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
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.repository.MeetingRepository

@HiltViewModel
class EventListViewModel @Inject constructor(private val repository: MeetingRepository) :
  ViewModel() {
  private val _listEvents = MutableStateFlow<List<EventDto>>(emptyList())
  val listEvents: StateFlow<List<EventDto>> = _listEvents.asStateFlow()

  init {
    getListEvents()
  }

  private fun getListEvents() {
    viewModelScope.launch {
      try {
        //_listEvents.value = repository.getAll()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun getEvent(id: Int): EventDto {
    return try {
      _listEvents.value[id]
    } catch (e: Resources.NotFoundException) {
      Log.e("ViewModelEventList", e.toString())
      EventDto()
    }
  }

  fun deleteEvent(id: Int) {
   // repository.delete(id)
  }
}
