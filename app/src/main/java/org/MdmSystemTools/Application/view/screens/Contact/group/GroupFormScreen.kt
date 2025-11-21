package org.MdmSystemTools.Application.view.screens.Contact.group

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.MdmSystemTools.Application.view.components.FieldDropdownMenuStyled
import org.MdmSystemTools.Application.view.components.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupFormScreen(
	onClickBackScreen: () -> Unit,
	onClickConfirmButton: () -> Unit,
	viewModel: GroupFormViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsState()
	val uiEvent = viewModel.uiEvent.collectAsStateWithLifecycle(null)
	val projectOptions by viewModel.projectOptions.collectAsState()
	val context = LocalContext.current
	val userClick = remember { mutableStateOf(false) }

	LaunchedEffect(userClick.value) {
		uiEvent.value?.let { event ->
			when (event) {
				is UiEvent.Success -> {
					Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
					onClickConfirmButton()
				}

				is UiEvent.Error -> {
					Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
				}
			}
		}
	}

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Cadastrar novo Grupo") },
				navigationIcon = {
					IconButton(onClick = onClickBackScreen) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
					}
				},
			)
		}
	) { paddingValues ->
		Column(modifier = Modifier.padding(paddingValues)) {
			OutlinedTextField(
				state = uiState.schedule,
				label = { Text("Horario") },
				modifier = Modifier
					.fillMaxWidth()
					.semantics { contentType = ContentType.PersonFullName },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
				inputTransformation = InputTransformation.maxLength(30),
			)
			FieldDropdownMenuStyled(
				"Projeto",
				Icons.Default.Flag,
				projectOptions,
				"Selecione um Projeto",
				uiState.projectId
			)
			Button(
				enabled = viewModel.isFormValid(uiState),
				modifier = Modifier.fillMaxWidth(),
				onClick = {
					userClick.value = !userClick.value
					viewModel.save(uiState)
				},
			) {
				Text("Confirmar")
			}
		}
	}
}
