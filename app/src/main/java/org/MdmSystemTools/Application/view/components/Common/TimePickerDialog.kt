package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimePickerDialog(
  title: String,
  hour: Int,
  minute: Int,
  onDismiss: () -> Unit,
  updateHourStart: (Int, Int) -> Unit,
) {
  var hour = hour
  var minute = minute
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(text = title, fontWeight = FontWeight.SemiBold) },
    text = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        // horas
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            "Hora",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
          )

          NumberPicker(
            value = hour,
            onValueChange = { hour = it },
            range = 0..23,
            formatter = { "%02d".format(it) },
          )
        }

        Text(text = ":", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // minutos
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            "Min",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
          )

          NumberPicker(
            value = minute,
            onValueChange = { minute = it },
            range = 0..59,
            formatter = { "%02d".format(it) },
          )
        }
      }
    },
    confirmButton = { TextButton(onClick = { updateHourStart(hour, minute) }) { Text("OK") } },
    dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
  )
}

@Composable
private fun NumberPicker(
  value: Int,
  onValueChange: (Int) -> Unit,
  range: IntRange,
  formatter: (Int) -> String = { it.toString() },
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    // Botão para aumentar
    IconButton(
      onClick = { if (value < range.last) onValueChange(value + 1) else onValueChange(range.first) }
    ) {
      Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Aumentar")
    }

    // Valor atual
    Card(
      colors =
        CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
      Text(
        text = formatter(value),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
      )
    }

    // Botão para diminuir
    IconButton(
      onClick = { if (value > range.first) onValueChange(value - 1) else onValueChange(range.last) }
    ) {
      Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Diminuir")
    }
  }
}
