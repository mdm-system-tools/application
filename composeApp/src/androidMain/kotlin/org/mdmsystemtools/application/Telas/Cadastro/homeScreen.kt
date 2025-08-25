package org.mdmsystemtools.application.Telas.Cadastro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.mdmsystemtools.application.componentes.ButtonFormAdd
import org.mdmsystemtools.application.componentes.QuickAccessButtons
import org.mdmsystemtools.application.componentes.SearchBar

@Composable
fun CadastrosScreen() {
  var showDialog by remember { mutableStateOf(false) }
	val modifier: Modifier = Modifier

  Box(modifier = Modifier.fillMaxSize()) {
    Column {
      SearchBar(
        placeholder = "Pesquisar cadastros...",
        onSearchChange = { searchText ->
        }
      )
      
      QuickAccessButtons(
        onLastMeetingClick = {
        },
        onLastSearchClick = {
        }
      )
    }

    Formulario(
      showDialog = showDialog,
      onDismiss = { showDialog = false }
    )

    ButtonFormAdd(Modifier.fillMaxSize(), onClick = { showDialog = true })
  }
}