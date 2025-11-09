package org.MdmSystemTools.Application.model.dto

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class EventDto(
  val title: String = "",
  val date: Long = 0L,
  val hourStart: String = "",
  val hourEnd: String = "",
  val local: String = "",
  val region: String = "",
  val project: String = "",
  val groups: GroupDto? = null,
  val color: Color = Color.Gray,
) {
  @Composable
  fun formatDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(date))
  }
}
