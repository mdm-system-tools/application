package org.MdmSystemTools.Application.view.screens.Registration

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun AssociateFormScreen(
	onClickConfirmButton: () -> Unit,
	viewModel: AssociateListViewModel = hiltViewModel()
) {
	val listOptions = (1..5).map { it.toString() }
	val nameState = rememberTextFieldState("")
	val numberCardState = rememberTextFieldState("")
	val groupState = rememberTextFieldState("")
	val listFields = listOf(
		"Nome Completo" to nameState,
		"Número da Carteirinha" to numberCardState,
	)

	Log.i("form", nameState.text as String)
	Log.i("form", numberCardState.text as String)
	Log.i("form", groupState.text as String)

	Column(modifier = Modifier.padding(20.dp)) {
		LazyColumn {
			items(listFields) { (label, state) ->
				OutlinedTextField(
					state = state,
					label = { Text(label) },
					modifier = Modifier.fillMaxWidth()
				)
			}
			item {
				FieldDropdownMenu("Grupo", listOptions, groupState)
			}
			item {
				Button(
					onClick = {
						viewModel.onSubmit(
							nameState.text as String, numberCardState.text as String,
							groupState.text as String
						)
						onClickConfirmButton()
					},
					modifier = Modifier.fillMaxWidth()
				) {
					Text("Confirmar")
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldDropdownMenu(title: String, menuOptions: List<String>, fieldState: TextFieldState) {
	var expanded: Boolean by remember { mutableStateOf(false) }

	ExposedDropdownMenuBox(
		expanded = expanded,
		onExpandedChange = { expanded = !expanded }
	) {
		OutlinedTextField(
			state = fieldState,
			label = { Text(title) },
			trailingIcon = {
				ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
			},
			readOnly = true,
			modifier = Modifier
        .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
        .fillMaxWidth()
		)

		ExposedDropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false },
		) {
			menuOptions.forEach { option ->
				DropdownMenuItem(
					text = { Text(option) },
					onClick = {
						fieldState.edit {
							delete(0, length)
							placeCursorAtEnd()
							append(option)
						}
						expanded = false
					}
				)
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