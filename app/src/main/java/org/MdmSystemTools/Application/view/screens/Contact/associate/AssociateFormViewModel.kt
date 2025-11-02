package org.MdmSystemTools.Application.view.screens.Contact.associate

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.io.IOException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.repository.AssociateRepository

sealed class UiEvent {
  data class Success(val message: String) : UiEvent()

  data class Error(val message: String) : UiEvent()
}

@HiltViewModel
class AssociateFormViewModel @Inject constructor(private val repository: AssociateRepository) :
  ViewModel() {

  val name: TextFieldState = TextFieldState()
  val numberCard: TextFieldState = TextFieldState()
  val groupId: TextFieldState = TextFieldState()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun validate(): Boolean {
    return name.text.isNotEmpty() && numberCard.text.isNotEmpty() && groupId.text.isNotEmpty()
  }

  fun onSubmit() {
    if (!validate()) {
      viewModelScope.launch { _uiEvent.emit(UiEvent.Error("Preencha todos os campos")) }
      return
    }
    val associate =
      try {
        AssociateDto(
          name.text.toString(),
          numberCard.text.toString().toInt(),
          groupId.text.toString().toInt(),
        )
      } catch (e: IOException) {
        viewModelScope.launch {
          _uiEvent.emit(UiEvent.Error("Erro ao criar associado: ${e.message}"))
        }
        return
      }

    viewModelScope.launch {
      try {
        repository.addAssociate(associate)
        _uiEvent.emit(UiEvent.Success("Associado salvo com sucesso"))
      } catch (e: Exception) {
        _uiEvent.emit(UiEvent.Error("Erro ao salvar: ${e.message}"))
      }
    }
  }
}
