package org.MdmSystemTools.Application.viewmodel.Meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.DTO.EventDto
import org.MdmSystemTools.Application.model.repository.EventRepository
import java.util.*
import jakarta.inject.Inject

@HiltViewModel
class MeetingViewModel @Inject constructor(
  private val eventRepository: EventRepository
) : ViewModel() {

  private val _currentMonth = MutableStateFlow(Calendar.getInstance().get(Calendar.MONTH))
  val currentMonth: StateFlow<Int> = _currentMonth.asStateFlow()

  private val _currentYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
  val currentYear: StateFlow<Int> = _currentYear.asStateFlow()

  private val _eventos = MutableStateFlow<List<EventDto>>(emptyList())
  val eventos: StateFlow<List<EventDto>> = _eventos.asStateFlow()

  init {
    carregarEventos()
  }

  fun navegarProximoMes() {
    viewModelScope.launch {
      if (_currentMonth.value == 11) {
        _currentMonth.value = 0
        _currentYear.value += 1
      } else {
        _currentMonth.value += 1
      }
    }
  }

  fun navegarMesAnterior() {
    viewModelScope.launch {
      if (_currentMonth.value == 0) {
        _currentMonth.value = 11
        _currentYear.value -= 1
      } else {
        _currentMonth.value -= 1
      }
    }
  }

  fun navegarParaMes(month: Int, year: Int) {
    viewModelScope.launch {
      _currentMonth.value = month
      _currentYear.value = year
    }
  }

  fun adicionarEvento(evento: EventDto) {
    viewModelScope.launch {
      try {
        eventRepository.adicionarEvento(evento)
        carregarEventos()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun removerEvento(eventoId: String) {
    viewModelScope.launch {
      try {
        eventRepository.removerEvento(eventoId)
        carregarEventos()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun obterEventosPorData(data: CalendarDateDto): List<EventDto> {
    return eventRepository.obterEventosPorData(data)
  }

  fun temEventosNaData(data: CalendarDateDto): Boolean {
    return eventRepository.temEventosNaData(data)
  }

  private fun carregarEventos() {
    viewModelScope.launch {
      try {
        _eventos.value = eventRepository.obterTodosEventos()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}