package org.MdmSystemTools.Application.view.components.Forms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Projetos predefinidos
private val projetosPredefinidos = listOf(
	"Sistema MDM",
	"Portal Web",
	"Aplicativo Mobile",
	"Sistema de Gestão",
	"Plataforma E-commerce",
	"Sistema de CRM",
	"Dashboard Analytics",
	"API Gateway",
	"Sistema de Monitoramento",
	"Plataforma de Treinamento"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjetoSelector(
	selectedProjeto: String,
	onProjetoChange: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	var expanded by remember { mutableStateOf(false) }

	Card(
		modifier = modifier.fillMaxWidth(),
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
		)
	) {
		Column(
			modifier = Modifier.padding(16.dp)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Icon(
					imageVector = Icons.Default.Work,
					contentDescription = "Projeto",
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier.size(20.dp)
				)
				Text(
					text = "Projeto",
					fontSize = 14.sp,
					fontWeight = FontWeight.Medium,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
				)
			}

			Spacer(modifier = Modifier.height(8.dp))

			ExposedDropdownMenuBox(
				expanded = expanded,
				onExpandedChange = { expanded = !expanded }
			) {
				val fillMaxWidth = Modifier
					.fillMaxWidth()
				TextField(
					value = selectedProjeto,
					onValueChange = { },
					readOnly = true,
					placeholder = {
						Text(
							text = "Selecione um projeto",
							color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
						)
					},
					trailingIcon = {
						Icon(
							imageVector = Icons.Default.ArrowDropDown,
							contentDescription = "Dropdown",
							tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
						)
					},
					colors = TextFieldDefaults.colors(
						focusedContainerColor = Color.Transparent,
						unfocusedContainerColor = Color.Transparent,
						focusedIndicatorColor = Color.Transparent,
						unfocusedIndicatorColor = Color.Transparent
					),
					modifier = fillMaxWidth
            .menuAnchor(
              type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
              enabled = true
            )

            .clickable { expanded = true }
				)

				ExposedDropdownMenu(
					expanded = expanded,
					onDismissRequest = { expanded = false }
				) {
					projetosPredefinidos.forEach { projeto ->
						DropdownMenuItem(
							text = { Text(projeto) },
							onClick = {
								onProjetoChange(projeto)
								expanded = false
							}
						)
					}
				}
			}
		}
	}
}