package org.MdmSystemTools.Application.view.components.Meeting

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.model.DTO.EventDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
	evento: EventDto,
	onDelete: () -> Unit,
	onEdit: () -> Unit,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	var showDeleteDialog by remember { mutableStateOf(false) }
	var showMenu by remember { mutableStateOf(false) }

	Card(
		modifier = modifier
			.fillMaxWidth()
			.clickable { onClick() },
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
		shape = RoundedCornerShape(16.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
		) {
			// Header com título e botão de menu
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(12.dp),
					modifier = Modifier.weight(1f)
				) {
					// Indicador de cor do grupo
					Box(
						modifier = Modifier
							.size(12.dp)
							.clip(CircleShape)
							.background(evento.groups?.cor ?: evento.color)
							.border(
								1.dp,
								MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
								CircleShape
							)
					)

					Text(
						text = evento.title,
						style = MaterialTheme.typography.titleMedium,
						fontWeight = FontWeight.Bold,
						maxLines = 1,
						overflow = TextOverflow.Ellipsis
					)
				}

				// Botão de menu/opções
				Box {
					IconButton(
						onClick = { showMenu = true },
						modifier = Modifier.size(32.dp)
					) {
						Icon(
							imageVector = Icons.Outlined.MoreVert,
							contentDescription = "Opções",
							tint = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.size(20.dp)
						)
					}

					// Mini menu dropdown
					DropdownMenu(
						expanded = showMenu,
						onDismissRequest = { showMenu = false }
					) {
						// Opção Editar
						DropdownMenuItem(
							text = {
								Row(
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.spacedBy(12.dp)
								) {
									Icon(
										imageVector = Icons.Default.Edit,
										contentDescription = "Editar",
										tint = MaterialTheme.colorScheme.primary,
										modifier = Modifier.size(18.dp)
									)
									Text(
										text = "Editar",
										style = MaterialTheme.typography.bodyMedium
									)
								}
							},
							onClick = {
								showMenu = false
								onEdit()
							}
						)

						// Divider
						HorizontalDivider()

						// Opção Deletar
						DropdownMenuItem(
							text = {
								Row(
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.spacedBy(12.dp)
								) {
									Icon(
										imageVector = Icons.Default.Delete,
										contentDescription = "Deletar",
										tint = MaterialTheme.colorScheme.error,
										modifier = Modifier.size(18.dp)
									)
									Text(
										text = "Remover",
										style = MaterialTheme.typography.bodyMedium,
										color = MaterialTheme.colorScheme.error
									)
								}
							},
							onClick = {
								showMenu = false
								showDeleteDialog = true
							}
						)
					}
				}
			}

			Spacer(modifier = Modifier.height(12.dp))

			// Informações principais
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(16.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				// Data
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(6.dp)
				) {
					Icon(
						imageVector = Icons.Default.CalendarToday,
						contentDescription = "Data",
						tint = MaterialTheme.colorScheme.primary,
						modifier = Modifier.size(16.dp)
					)
					Text(
						text = "${evento.date.day}/${evento.date.month + 1}",
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Medium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}

				// Horário
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(6.dp)
				) {
					Icon(
						imageVector = Icons.Default.Schedule,
						contentDescription = "Horário",
						tint = MaterialTheme.colorScheme.primary,
						modifier = Modifier.size(16.dp)
					)
					Text(
						text = "${evento.hourStart} - ${evento.hourEnd}",
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Medium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}

			// Descrição (se houver)
			if (evento.description.isNotBlank()) {
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = evento.description,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis
				)
			}

			// Local e Grupo (se houver)
			val hasExtraInfo = evento.local.isNotBlank() || evento.groups != null
			if (hasExtraInfo) {
				Spacer(modifier = Modifier.height(8.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(16.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					// Local
					if (evento.local.isNotBlank()) {
						Row(
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.spacedBy(4.dp)
						) {
							Icon(
								imageVector = Icons.Default.LocationOn,
								contentDescription = "Local",
								tint = MaterialTheme.colorScheme.secondary,
								modifier = Modifier.size(14.dp)
							)
							Text(
								text = evento.local,
								style = MaterialTheme.typography.bodySmall,
								color = MaterialTheme.colorScheme.onSurfaceVariant,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}
					}

					// Grupo
					evento.groups?.let { grupo ->
						Row(
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.spacedBy(4.dp)
						) {
							Icon(
								imageVector = Icons.Default.Groups,
								contentDescription = "Grupo",
								tint = MaterialTheme.colorScheme.secondary,
								modifier = Modifier.size(14.dp)
							)
							Text(
								text = grupo.nome,
								style = MaterialTheme.typography.bodySmall,
								color = MaterialTheme.colorScheme.onSurfaceVariant,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}
					}
				}
			}
		}
	}

	// Diálogo de confirmação para deletar
	if (showDeleteDialog) {
		AlertDialog(
			onDismissRequest = { showDeleteDialog = false },
			title = {
				Text("Remover Reunião")
			},
			text = {
				Text("Tem certeza que deseja remover a reunião \"${evento.title}\"?")
			},
			confirmButton = {
				TextButton(
					onClick = {
						onDelete()
						showDeleteDialog = false
					},
					colors = ButtonDefaults.textButtonColors(
						contentColor = MaterialTheme.colorScheme.error
					)
				) {
					Text("Remover")
				}
			},
			dismissButton = {
				TextButton(onClick = { showDeleteDialog = false }) {
					Text("Cancelar")
				}
			}
		)
	}
}