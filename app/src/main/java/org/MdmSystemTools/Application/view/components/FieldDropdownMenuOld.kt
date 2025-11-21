package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldDropdownMenu(
	title: String,
	menuOptions: List<DropdownOption>,
	fieldState: TextFieldState
) {
	var expanded: Boolean by remember { mutableStateOf(false) }

	ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
		OutlinedTextField(
			state = fieldState,
			label = { Text(title) },
			trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
			readOnly = true,
			modifier =
				Modifier
          .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
          .fillMaxWidth(),
		)

		ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
			menuOptions.forEach { option ->
				DropdownMenuItem(
					text = { Text(option.name) },
					onClick = {
						fieldState.edit {
							delete(0, length)
							placeCursorAtEnd()
							append(option.id.toString())
						}
						expanded = false
					},
				)
			}
		}
	}
}