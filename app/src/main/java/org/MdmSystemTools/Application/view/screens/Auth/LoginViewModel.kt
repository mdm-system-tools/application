package org.MdmSystemTools.Application.view.screens.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.entity.Login
import org.MdmSystemTools.Application.model.repository.AuthRepository
import javax.inject.Inject

data class LoginUiState(
  val isLoading: Boolean = false,
  val errorMessage: String = "",
  val isSuccess: Boolean = false,
  val user: Login? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val authRepository: AuthRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(LoginUiState())
  val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

  fun login(cpf: String, password: String) {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")

      val result = authRepository.login(cpf, password)
      result.onSuccess { user ->
        _uiState.value = _uiState.value.copy(
          isLoading = false,
          isSuccess = true,
          user = user
        )
      }
      result.onFailure { exception ->
        _uiState.value = _uiState.value.copy(
          isLoading = false,
          errorMessage = exception.message ?: "Erro ao fazer login"
        )
      }
    }
  }

  fun clearError() {
    _uiState.value = _uiState.value.copy(errorMessage = "")
  }

  fun resetState() {
    _uiState.value = LoginUiState()
  }
}
