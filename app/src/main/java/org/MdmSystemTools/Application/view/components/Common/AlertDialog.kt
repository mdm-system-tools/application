package org.MdmSystemTools.Application.view.components.Common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AlertDialog(
  title: String,
  text: String,
  icon: ImageVector,
  confirmButtonText: String,
  dismissButtonText: String,
  onClickDismiss: () -> Unit,
  onClickConfirm: () -> Unit,
) {
  AlertDialog(
    icon = { Icon(icon, contentDescription = null) },
    title = { Text(text = title) },
    text = { Text(text = text) },
    onDismissRequest = { onClickDismiss() },
    confirmButton = { TextButton(onClick = { onClickConfirm() }) { Text(confirmButtonText) } },
    dismissButton = { TextButton(onClick = { onClickDismiss() }) { Text(dismissButtonText) } },
  )
}
