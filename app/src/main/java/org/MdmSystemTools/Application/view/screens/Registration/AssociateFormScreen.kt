package org.MdmSystemTools.Application.view.screens.Registration

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun AssociateFormScreen(
	onClickConfirmButton: () -> Unit, viewModel: AssociateListViewModel = hiltViewModel()
) {
	val listOptions = (1..5).map { it.toString() }

	Column(modifier = Modifier.padding(20.dp)) {
		OutlinedTextField(
			state = viewModel.name,
			label = { Text("Nome Completo") },
			modifier = Modifier.fillMaxWidth(),
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Text
			),
			inputTransformation = InputTransformation.maxLength(30)
		)
		OutlinedTextField(
			state = viewModel.numberCard,
			label = { Text("Numero da Carterinha") },
			modifier = Modifier.fillMaxWidth(),
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Number
			),
			inputTransformation = InputTransformation.maxLength(10).then {
				if (!asCharSequence().isDigitsOnly()) {
					revertAllChanges()
				}
			},
		)
		FieldDropdownMenu("Grupo", listOptions, viewModel.groupId)
		Button(
			enabled = viewModel.validate(),
			modifier = Modifier.fillMaxWidth(),
			onClick = {
				viewModel.onSubmit()
				onClickConfirmButton()
			}
		) { Text("Confirmar") }
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldDropdownMenu(title: String, menuOptions: List<String>, fieldState: TextFieldState) {
	var expanded: Boolean by remember { mutableStateOf(false) }

	ExposedDropdownMenuBox(
		expanded = expanded, onExpandedChange = { expanded = !expanded }) {
		OutlinedTextField(
			state = fieldState,
			label = { Text(title) },
			trailingIcon = {
				ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
			},
			readOnly = true,
			modifier = Modifier
        .menuAnchor(
          type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true
        )
        .fillMaxWidth()
		)

		ExposedDropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false },
		) {
			menuOptions.forEach { option ->
				DropdownMenuItem(text = { Text(option) }, onClick = {
					fieldState.edit {
						delete(0, length)
						placeCursorAtEnd()
						append(option)
					}
					expanded = false
				})
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun FieldDropdownMenuPreview() {
	val userOption = rememberTextFieldState("")
	val menuOptions = (1..5).map { it.toString() }
	val title = "Selecionar numero"

	FieldDropdownMenu(title, menuOptions, userOption)
	Log.i("form", "dados atualizados: $userOption")
}

@Preview(showBackground = true)
@Composable
private fun FormFieldsPreview() {
	//FormFields()
}