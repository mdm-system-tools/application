package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.DTO.GroupDto

interface GroupRepository {
	fun getListGroups(): List<GroupDto>
}