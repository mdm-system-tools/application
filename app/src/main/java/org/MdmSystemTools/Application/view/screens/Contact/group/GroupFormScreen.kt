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
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.view.screens.Contact.associate.FieldDropdownMenu
import org.MdmSystemTools.Application.view.screens.Contact.associate.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupFormScreen(
  onClickBackScreen: () -> Unit,
  onClickConfirmButton: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: GroupFormViewModel = hiltViewModel(),
) {
  val listOptions = (1..5).map { it.toString() }
  val context = LocalContext.current

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
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
        title = { Text("Cadastrar novo Associado") },
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
        state = viewModel.schedule,
        label = { Text("Nome Completo") },
        modifier = Modifier.fillMaxWidth().semantics { contentType = ContentType.PersonFullName },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        inputTransformation = InputTransformation.maxLength(30),
      )
      FieldDropdownMenu("Grupo", listOptions, viewModel.groupId)
      Button(
        enabled = viewModel.validate(),
        modifier = Modifier.fillMaxWidth(),
        onClick = { viewModel.onSubmit() },
      ) {
        Text("Confirmar")
      }
    }
  }
}
