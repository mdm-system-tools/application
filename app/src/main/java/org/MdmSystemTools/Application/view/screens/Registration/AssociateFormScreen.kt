package org.MdmSystemTools.Application.view.screens.Registration

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.MdmSystemTools.Application.model.dto.AssociateDto

sealed interface field {
	val label: String
	var data: String

	data object name : field {
		override val label = "Nome"
		override var data = ""
	}

	data object group : field {
		override val label = "Grupo"
		override var data = ""
	}

	data object numberCard : field {
		override val label = "Numero da Carterinha"
		override var data = ""
	}
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun AssociateFormScreen(
	onClickConfirmButton: () -> Unit,
	viewModel: AssociateListViewModel = hiltViewModel()
) {
	val fields = remember {
		mutableStateListOf(
			field.name, field.group, field.numberCard
		)
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
			//TODO BUG não é possivel escrever nos campos
			items(fields) { field ->
				OutlinedTextField(
					value = field.data,
					onValueChange = { field.data = it },
					label = { Text(field.label) },
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 4.dp, horizontal = 8.dp)
				)
			}
		}

		Row {
			Button(
				onClick = {
					val newAssociate = AssociateDto(101010, 1, fields[1].data)
					viewModel.createAssociate(newAssociate)
					onClickConfirmButton()
					Log.i("viewmodel", "FormScreen - create new associate: $newAssociate")
				},
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
	AssociateFormScreen({})
}