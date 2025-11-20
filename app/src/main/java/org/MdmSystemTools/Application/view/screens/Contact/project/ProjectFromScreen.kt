package org.MdmSystemTools.Application.view.screens.Contact.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectFormScreen(
    onBackClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onCriarProjetoClick: () -> Unit = {},
    viewModel: ProjectFormViewModel = hiltViewModel()
) {
    var nomeProjeto by remember { mutableStateOf("") }
    var regiao by remember { mutableStateOf("") }
    var valorMensal by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val uiEvent: ProjectFormUiEvent? by viewModel.uiEvent.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(uiEvent) {
        when (uiEvent) {
            is ProjectFormUiEvent.Success -> {
                snackbarHostState.showSnackbar((uiEvent as ProjectFormUiEvent.Success).message)
                // Aguarda um pouco para o usuário ver a mensagem antes de voltar
                delay(500)
                onBackClick()
            }
            is ProjectFormUiEvent.Error -> {
                snackbarHostState.showSnackbar((uiEvent as ProjectFormUiEvent.Error).message)
            }
            null -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Novo Projeto") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            Text(
                text = "Nome do Projeto",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = nomeProjeto,
                onValueChange = {
                    nomeProjeto = it
                    viewModel.name.edit { replace(0, length, it) }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text("Digite o nome do projeto") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Região
            Text(
                text = "Região",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = regiao,
                onValueChange = {
                    regiao = it
                    viewModel.region.edit { replace(0, length, it) }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text("Digite a região") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Valor Mensal por Associado",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = valorMensal,
                onValueChange = {
                    valorMensal = it
                    viewModel.value.edit { replace(0, length, it) }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text("R$ 0,00") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onCancelarClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { viewModel.onSubmit() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text("Criar Projeto")
                }
            }
        }
    }
}
