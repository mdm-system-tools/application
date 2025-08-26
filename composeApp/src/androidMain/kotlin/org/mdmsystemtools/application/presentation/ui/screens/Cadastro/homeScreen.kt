package org.mdmsystemtools.application.presentation.ui.screens.Cadastro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.mdmsystemtools.application.presentation.ui.components.ButtonFormAdd
import org.mdmsystemtools.application.presentation.ui.components.QuickAccessButtons
import org.mdmsystemtools.application.presentation.ui.components.SearchBar

@Composable
fun CadastrosScreen(navController: NavHostController) {
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

    //Formulario(
    //  showDialog = showDialog,
    //  onDismiss = { showDialog = false }
    //)

    ButtonFormAdd(Modifier.fillMaxSize(), onClick = {
			navController.navigate("Formulario")

		})
  }
}