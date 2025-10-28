package org.MdmSystemTools.Application.view.components.Common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CicleIcon(icon: ImageVector, color: Color) {
  Box(contentAlignment = Alignment.Center) {
    Icon(
      modifier = Modifier.size(18.dp),
      imageVector = icon,
      contentDescription = null,
      tint = color,
    )
  }
}
