package org.MdmSystemTools.Application.view.screens.Contact.projetc

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjetoDetalhesScreen(
    projetoId: Int = 1,
    onBackClick: () -> Unit = {},
    onGrupoClick: (GrupoProjetoUi) -> Unit = {},
    viewModel: ProjetoDetalhesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(projetoId) {
        viewModel.loadProjetoDetalhes(projetoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Projeto") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { /* menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Mais opções")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            item {
                Spacer(Modifier.height(8.dp))

                // Nome + data + chip status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = uiState.projetoNome,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Início: ${uiState.dataInicio}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    AssistChip(
                        onClick = { },
                        label = { Text(uiState.status) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = androidx.compose.ui.graphics.Color(0xFFE8F7ED),
                            labelColor = androidx.compose.ui.graphics.Color(0xFF34A853)
                        )
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Cards de resumo (Região, Valor, Grupos, Associados)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoResumoCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.LocationOn,
                        titulo = "Região",
                        valor = uiState.regiao,
                        corPrincipal = androidx.compose.ui.graphics.Color(0xFF3D7BFF),
                        corFundo = androidx.compose.ui.graphics.Color(0xFFE8F0FF)
                    )
                    InfoResumoCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.AttachMoney,
                        titulo = "Valor",
                        valor = uiState.valor,
                        corPrincipal = androidx.compose.ui.graphics.Color(0xFF34A853),
                        corFundo = androidx.compose.ui.graphics.Color(0xFFE8F7ED)
                    )
                    InfoResumoCard(
                        modifier = Modifier.weight(	1f),
                        icon = Icons.Default.Groups,
                        titulo = "Grupos",
                        valor = uiState.totalGrupos.toString(),
                        corPrincipal = androidx.compose.ui.graphics.Color(0xFF9C27B0),
                        corFundo = androidx.compose.ui.graphics.Color(0xFFF5E6FF)
                    )
                    InfoResumoCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Person,
                        titulo = "Associados",
                        valor = uiState.totalAssociados.toString(),
                        corPrincipal = androidx.compose.ui.graphics.Color(0xFFFF8A00),
                        corFundo = androidx.compose.ui.graphics.Color(0xFFFFF0E0)
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Grupos do Projeto",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))
            }

            // Lista de grupos
            items(uiState.grupos) { grupo ->
                GrupoProjetoCard(
                    grupo = grupo,
                    onClick = { onGrupoClick(grupo) }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

/* ------------ MODELOS SIMPLES ------------ */

data class GrupoProjetoUi(
    val nome: String,
    val qtdAssociados: Int
)

/* ------------ COMPONENTES REUTILIZÁVEIS ------------ */

@Composable
fun InfoResumoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    titulo: String,
    valor: String,
    corPrincipal: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    corFundo: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = corFundo
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    icon,
                    contentDescription = titulo,
                    modifier = Modifier.size(18.dp),
                    tint = corPrincipal
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.labelSmall,
                    color = corPrincipal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = corPrincipal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun GrupoProjetoCard(
    grupo: GrupoProjetoUi,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {

            // Linha título + hora
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ícone circular
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Horário",
                        modifier = Modifier.size(22.dp),
                    )
                }

                Spacer(Modifier.width(12.dp))

                Text(
                    text = grupo.nome,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Spacer(Modifier.height(8.dp))

            // Linha quantidade + botão "Ver Grupo"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "Associados",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "${grupo.qtdAssociados} Associados",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                OutlinedButton(
                    onClick = onClick,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Ver Grupo")
                }
            }
        }
    }
}
