package org.MdmSystemTools.Application.view.screens.Registration

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.repository.ListAssociateRepository

@HiltViewModel
class AssociateFormViewModel @Inject constructor(
	private val repository: ListAssociateRepository
) : ViewModel() {

	val name: TextFieldState = TextFieldState()
	val numberCard: TextFieldState = TextFieldState()
	val groupId: TextFieldState = TextFieldState()

	fun validate(): Boolean {
		try {
			val validateName = name.text.isNotEmpty()
			val validateNumberCard = numberCard.text.isNotEmpty()
			val validateGroup = groupId.text.isNotEmpty()
			return validateName && validateNumberCard && validateGroup
		} catch (e: Exception) {
			Log.e("ViewModelAssociateForm", e.message.toString())
			return false
		}
	}

	fun createAssociate(): AssociateDto {
		if (validate()) {
			try {
				return AssociateDto(
					name.text.toString(),
					numberCard.text.toString().toInt(),
					groupId.text.toString().toInt()
				)
			} catch (e: Exception) {
				throw Exception("erro na criação do objeto associate: ${e.toString()}")
			}
		} else throw Exception("Tentativa de criar o objeto associate com campos vazios")
	}

	fun onSubmit(associate: AssociateDto) {
		try {
			repository.addAssociate(associate)
		} catch (e: Exception) {
			throw Exception("Erro ao tentar enviar objeto associate para o repository: ${e.toString()}")
		}
	}
}