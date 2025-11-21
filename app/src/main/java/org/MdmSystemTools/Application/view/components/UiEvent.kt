package org.MdmSystemTools.Application.view.components

sealed class UiEvent {
	data class Success(val message: String) : UiEvent()
	data class Error(val message: String) : UiEvent()
}
