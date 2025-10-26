package org.MdmSystemTools.Application.view.screens.Calendar

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import org.MdmSystemTools.Application.model.dto.EventDate
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.model.repository.EventRepository
import androidx.compose.ui.graphics.Color
import java.util.UUID

@HiltViewModel
class EventFormViewModel @Inject constructor(
	private val repository: EventRepository
) : ViewModel() {

	val title: TextFieldState = TextFieldState()
	val description: TextFieldState = TextFieldState()
	val day: TextFieldState = TextFieldState()
	val month: TextFieldState = TextFieldState()
	val year: TextFieldState = TextFieldState()
	val hourStart: TextFieldState = TextFieldState()
	val hourEnd: TextFieldState = TextFieldState()
	val local: TextFieldState = TextFieldState()
	val region: TextFieldState = TextFieldState()
	val project: TextFieldState = TextFieldState()
	val groupId: TextFieldState = TextFieldState()

	fun validate(): Boolean {
		try {
			val validateTitle = title.text.isNotEmpty()
			val validateDescription = description.text.isNotEmpty()
			val validateDay = day.text.isNotEmpty()
			val validateMonth = month.text.isNotEmpty()
			val validateYear = year.text.isNotEmpty()
			val validateHourStart = hourStart.text.isNotEmpty()
			val validateHourEnd = hourEnd.text.isNotEmpty()
			val validateLocal = local.text.isNotEmpty()
			val validateRegion = region.text.isNotEmpty()
			val validateProject = project.text.isNotEmpty()
			val validateGroup = groupId.text.isNotEmpty()

			return validateTitle && validateDescription && validateDay &&
					validateMonth && validateYear && validateHourStart &&
					validateHourEnd && validateLocal && validateRegion &&
					validateProject && validateGroup
		} catch (e: Exception) {
			Log.e("EventFormViewModel", e.message.toString())
			return false
		}
	}

	fun createEvent(): EventDto {
		if (validate()) {
			try {
				val eventDate = EventDate(
					day = day.text.toString().toInt(),
					month = month.text.toString().toInt(),
					year = year.text.toString().toInt()
				)

				val group = GroupDto(
					id = groupId.text.toString(),
					name = "Grupo ${groupId.text}", // TODO: Buscar do repository
					color = Color(0xFF1C6AEA) // TODO: Buscar do repository
				)

				return EventDto(
					id = UUID.randomUUID().toString(),
					title = title.text.toString(),
					description = description.text.toString(),
					date = eventDate,
					hourStart = hourStart.text.toString(),
					hourEnd = hourEnd.text.toString(),
					local = local.text.toString(),
					region = region.text.toString(),
					project = project.text.toString(),
					groups = group,
					color = Color(0xFF1C6AEA) // TODO: Permitir seleção de cor
				)
			} catch (e: Exception) {
				throw Exception("Erro na criação do objeto event: ${e.toString()}")
			}
		} else {
			throw Exception("Tentativa de criar o objeto event com campos vazios")
		}
	}

	fun onSubmit(event: EventDto) {
		try {
			repository.addEvent(event)
		} catch (e: Exception) {
			throw Exception("Erro ao tentar enviar objeto event para o repository: ${e.toString()}")
		}
	}
}
