package org.mdmsystemtools.application.Telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun FilledButtonExample(onClick: () -> Unit) {
  Button(onClick = { onClick() }) {
    Text("Filled")
  }
}

@Composable
fun FactoryAssociatedView(name: String, cpf: String, grupo: String) {
  Column {
    Text(name)
    Text(cpf)
    Text(grupo)
  }
}

@Composable
fun BotaoCard(
  titulo: String,
  descricao: String,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Text(
        text = titulo,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
      )
      Text(
        text = descricao,
        fontSize = 14.sp
      )
    }
  }
}

@Composable
fun CadastrosScreen() {
  // 1. Variável de estado para controlar a visibilidade do dialog
  var showDialog =  remember { mutableStateOf(false) }

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // 2. O botão que muda o estado
    Button(
      onClick = { showDialog = true } // Altera o estado para true
    ) {
      Text("Abrir Nova Tela")
    }
  }

  // 3. O Composable do dialog que só é exibido se o estado for true
  if (showDialog) {
    Dialog(
      onDismissRequest = {
        // Altera o estado para false ao clicar fora do dialog
        showDialog = false
      }
    ) {
      // Conteúdo da sua "nova tela"
      Column(
        modifier = Modifier
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(text = "Esta é a tela de detalhes que abriu por cima!")
        Button(
          onClick = { showDialog = false } // Altera o estado para false para fechar
        ) {
          Text("Fechar")
        }
      }
    }
  }
}