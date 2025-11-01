package org.MdmSystemTools.Application.view.screens.Meeting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.MdmSystemTools.Application.model.repository.AssociateRepository

sealed class UiEvent {
  data class Success(val message: String) : UiEvent()

  data class Error(val message: String) : UiEvent()
}

@HiltViewModel
class RollCallViewModel @Inject constructor(private val repository: AssociateRepository) :
  ViewModel() {
  val _uiState = MutableSharedFlow<UiEvent>()
  val uiState = _uiState.asSharedFlow()
}
