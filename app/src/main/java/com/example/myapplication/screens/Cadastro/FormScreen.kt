package org.mdmsystemtools.application.presentation.ui.screens.Cadastro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

enum class MeusCampos(val label: String, var data: String = "") {
	nome("Nome"),
	email("Email"),
	telefone("Telefone"),
	cpf("CPF")
}


// TODO Transformar o dialog em uma tela
@Composable
fun FormScreen(
	showDialog: Boolean = false,
	onDismiss: () -> Unit = {},
	title: String = "Cadastro",
	buttonText: String = "Salvar"
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Text("TODO Implementar uma tela de formulario")
	}
	if (showDialog) {
		var nome by remember { mutableStateOf("") }
		var email by remember { mutableStateOf("") }
		var telefone by remember { mutableStateOf("") }
		var cpf by remember { mutableStateOf("") }
		val campos = remember {
			MeusCampos.entries.associateWith { mutableStateOf(it.data) }
		}

		Dialog(
			onDismissRequest = onDismiss
		) {
			Box(
				modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
			) {

				LazyColumn (){
					items(MeusCampos.entries.toTypedArray()) { campo ->
						val state = campos[campo]!!
						OutlinedTextField(
							value = state.value,
							onValueChange = { state.value = it },
							label = { Text(campo.label) },
							modifier = Modifier
								.fillMaxWidth()
								.padding(vertical = 4.dp, horizontal = 8.dp)
						)
					}
				}

				Column(
					modifier = Modifier.padding(20.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.spacedBy(16.dp)
				) {
					Text(
						text = title,
						fontSize = 18.sp
					)
				}
				//OutlinedTextField(
				//	value = nome,
				//	onValueChange = { nome = it },
				//	label = { Text("Nome") },
				//	modifier = Modifier.fillMaxWidth()
				//)

				//OutlinedTextField(
				//	value = cpf,
				//	onValueChange = { nome = it },
				//	label = { Text("CPF") },
				//	modifier = Modifier.fillMaxWidth()
				//)

				//OutlinedTextField(
				//	value = email,
				//	onValueChange = { email = it },
				//	label = { Text("Email") },
				//	modifier = Modifier.fillMaxWidth()
				//)

				//OutlinedTextField(
				//	value = telefone,
				//	onValueChange = { telefone = it },
				//	label = { Text("Telefone") },
				//	modifier = Modifier.fillMaxWidth()
				//)

				//Button(
				//	onClick = {
				//		onDismiss()
				//	},
				//	modifier = Modifier.fillMaxWidth()
				//) {
				//	Text(buttonText)
				//}
			}
		}
	}
}
