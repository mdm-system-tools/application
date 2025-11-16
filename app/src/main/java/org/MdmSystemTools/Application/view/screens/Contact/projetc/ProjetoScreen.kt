package org.MdmSystemTools.Application.view.screens.Contact.projetc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.MdmSystemTools.Application.view.components.generic.ListScreenBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjetoScreen(
    onBackClick: () -> Unit = {},
    onProjetoClick: (org.MdmSystemTools.Application.model.entity.Project) -> Unit = {},
    onAddProjetoClick: () -> Unit = {},
    viewModel: ProjectViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListScreenBuilder(
        items = uiState.projects,
        searchPlaceholder = "Search Project",
        onAddClick = onAddProjetoClick,
        itemContent = { projeto, onItemClick ->
            ProjetoCard(projeto = projeto, onClick = onItemClick)
        },
        onItemClick = onProjetoClick
    )
}

@Composable
fun ProjetoCard(
    projeto: org.MdmSystemTools.Application.model.entity.Project,
    onClick: (org.MdmSystemTools.Application.model.entity.Project) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(projeto) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFE6D9FF),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = projeto.id.toString(),
                        color = Color(0xFF8A4FFF),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = projeto.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = "Região: ${projeto.region}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Valor: R$ ${projeto.value}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.DarkGray
            )
        }
    }
}
