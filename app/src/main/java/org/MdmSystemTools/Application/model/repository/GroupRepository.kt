package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.GroupDto

interface GroupRepository {
	fun getListGroups(): List<GroupDto>
}