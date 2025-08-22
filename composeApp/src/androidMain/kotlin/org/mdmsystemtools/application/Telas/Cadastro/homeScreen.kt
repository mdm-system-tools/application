package org.mdmsystemtools.application.Telas.Cadastro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import org.mdmsystemtools.application.componentes.ButtonFormAdd

@Composable
fun CadastrosScreen() {
  var showDialog by remember { mutableStateOf(false) }
	val modifier: Modifier = Modifier

  ButtonFormAdd(modifier, onClick = { showDialog = true })

  Formulario(
    showDialog = showDialog,
    onDismiss = { showDialog = false }
  )
}