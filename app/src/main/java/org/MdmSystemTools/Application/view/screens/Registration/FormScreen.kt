package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//TODO Troca a tipagem
enum class fields(val label: String, var data: String = "") {
	nome("Nome"),
	email("Email"),
	telefone("Telefone"),
	cpf("CPF")
}

@Composable
fun FormScreen(onClick: () -> Unit) {
	//TODO implementar uma forma de registrar cadastro
	var nome by remember { mutableStateOf("") }
	var email by remember { mutableStateOf("") }
	var telefone by remember { mutableStateOf("") }
	var cpf by remember { mutableStateOf("") }
	val campos = remember {
		fields.entries.associateWith { mutableStateOf(it.data) }
	}
	Column(
		Modifier.padding(20.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Text(
			text = "Cadastro",
			fontSize = 18.sp
		)
		LazyColumn {
			items(fields.entries.toTypedArray()) { field ->
				val state = campos[field]!!
				OutlinedTextField(
					value = state.value,
					onValueChange = { state.value = it },
					label = { Text(field.label) },
					modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
				)
			}
		}

		Row {
			Button(
				onClick = onClick,
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Criar")
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun FormScreenPreview() {
	FormScreen({})
}