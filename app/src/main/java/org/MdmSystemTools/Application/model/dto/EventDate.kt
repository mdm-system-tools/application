package org.MdmSystemTools.Application.model.dto

data class EventDate(
	val day: Int,
	val month: Int,
	val year: Int
){
	override fun toString(): String {
		return "$day/$month/$year"
	}
}
