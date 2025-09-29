package org.MdmSystemTools.Application.view.components.Forms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.model.DTO.GroupDto
import org.MdmSystemTools.Application.model.repository.GroupRepositoryImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupSelector(
	selectedGrupo: GroupDto?,
	onGrupoChange: (GroupDto?) -> Unit,
	modifier: Modifier = Modifier
) {
	var expanded by remember { mutableStateOf(false) }
	//TODO essa não é a forma mais correta mas vai funcionar por enquanto
	val groups = GroupRepositoryImpl()

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
					imageVector = Icons.Default.Groups,
					contentDescription = "Grupo",
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier.size(20.dp)
				)
				Text(
					text = "Grupo",
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
				GrupoDisplayField(
					selectedGrupo = selectedGrupo,
					onClick = { expanded = true },
					modifier = Modifier.menuAnchor()
				)

				ExposedDropdownMenu(
					expanded = expanded,
					onDismissRequest = { expanded = false },
					modifier = Modifier.heightIn(max = 300.dp)
				) {
					// Opção "Nenhum grupo"
					DropdownMenuItem(
						text = {
							Row(
								verticalAlignment = Alignment.CenterVertically,
								horizontalArrangement = Arrangement.spacedBy(12.dp)
							) {
								Box(
									modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .border(1.dp, Color.Gray, CircleShape)
								)
								Text("Nenhum grupo")
							}
						},
						onClick = {
							onGrupoChange(null)
							expanded = false
						}
					)

					// Divider
					Divider(modifier = Modifier.padding(vertical = 4.dp))

					// Grupos disponíveis
					groups.getListGroups().forEach { grupo ->
						DropdownMenuItem(
							text = {
								Row(
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.spacedBy(12.dp)
								) {
									Box(
										modifier = Modifier
                      .size(24.dp)
                      .clip(CircleShape)
                      .background(grupo.cor)
                      .border(
                        1.dp,
                        if (selectedGrupo?.id == grupo.id) {
                          MaterialTheme.colorScheme.primary
                        } else {
                          Color.Transparent
                        },
                        CircleShape
                      )
									)
									Column {
										Text(
											text = grupo.nome,
											fontWeight = FontWeight.Medium
										)
										if (grupo.descricao.isNotEmpty()) {
											Text(
												text = grupo.descricao,
												fontSize = 12.sp,
												color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
											)
										}
									}
								}
							},
							onClick = {
								onGrupoChange(grupo)
								expanded = false
							}
						)
					}
				}
			}
		}
	}
}

@Composable
private fun GrupoDisplayField(
	selectedGrupo: GroupDto?,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	TextField(
		value = selectedGrupo?.nome ?: "",
		onValueChange = { },
		readOnly = true,
		placeholder = {
			Text(
				text = "Selecione um grupo",
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
			)
		},
		leadingIcon = if (selectedGrupo != null) {
			{
				Box(
					modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(selectedGrupo.cor)
				)
			}
		} else null,
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
		modifier = modifier
      .fillMaxWidth()
      .clickable { onClick() }
	)
}