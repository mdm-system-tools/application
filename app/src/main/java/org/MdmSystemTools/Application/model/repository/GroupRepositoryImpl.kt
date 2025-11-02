package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.GroupDto

class GroupRepositoryImpl : GroupRepository {

  val listGroup = mutableListOf<GroupDto>()

  init {
    addGroup(GroupDto(schedule = "Desenvolvimento"))
    addGroup(GroupDto(schedule = "Design"))
  }

  override fun getListGroups(): List<GroupDto> {
    return listGroup
  }

  override fun addGroup(group: GroupDto) {
    listGroup.add(group)
  }

  override fun deleteGroupById(id: Int) {
    listGroup.removeAt(id)
  }
}
