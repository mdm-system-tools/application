package org.MdmSystemTools.Application.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Register
import org.MdmSystemTools.Application.model.repository.AuthRepository
import javax.inject.Inject

data class RegisterUiState(
  val isLoading: Boolean = false,
  val errorMessage: String = "",
  val isSuccess: Boolean = false,
  val register: Register? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
  private val authRepository: AuthRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(RegisterUiState())
  val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

  fun register(register: Register) {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")

      val result = authRepository.register(register)
      result.onSuccess { newRegister ->
        _uiState.value = _uiState.value.copy(
          isLoading = false,
          isSuccess = true,
          register = newRegister
        )
      }
      result.onFailure { exception ->
        _uiState.value = _uiState.value.copy(
          isLoading = false,
          errorMessage = exception.message ?: "Erro ao registrar"
        )
      }
    }
  }

  fun clearError() {
    _uiState.value = _uiState.value.copy(errorMessage = "")
  }

  fun resetState() {
    _uiState.value = RegisterUiState()
  }
}
