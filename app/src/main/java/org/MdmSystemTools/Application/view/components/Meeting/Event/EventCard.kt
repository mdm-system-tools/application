package org.MdmSystemTools.Application.view.components.Meeting.Event

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
import org.MdmSystemTools.Application.model.dto.EventDto
import org.MdmSystemTools.Application.view.theme.AppConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
	event: EventDto,
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
		elevation = CardDefaults.cardElevation(defaultElevation = AppConstants.ComponentSize.cardElevation),
		shape = RoundedCornerShape(AppConstants.CornerRadius.large),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(AppConstants.Spacing.medium)
		) {
			// Header com título e botão de menu
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.smallPlus),
					modifier = Modifier.weight(1f)
				) {
					// Indicador de cor do grupo
					Box(
						modifier = Modifier
							.size(AppConstants.ComponentSize.iconTiny)
							.clip(CircleShape)
							.background(event.groups?.cor ?: event.color)
							.border(
								AppConstants.ComponentSize.borderWidth,
								MaterialTheme.colorScheme.outline.copy(alpha = AppConstants.Alpha.disabled),
								CircleShape
							)
					)

					Text(
						text = event.title,
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
						modifier = Modifier.size(AppConstants.ComponentSize.iconButton)
					) {
						Icon(
							imageVector = Icons.Outlined.MoreVert,
							contentDescription = "Opções",
							tint = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.size(AppConstants.ComponentSize.iconMedium)
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
									horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.smallPlus)
								) {
									Icon(
										imageVector = Icons.Default.Edit,
										contentDescription = "Editar",
										tint = MaterialTheme.colorScheme.primary,
										modifier = Modifier.size(AppConstants.FontSize.extraLarge.value.dp)
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
									horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.smallPlus)
								) {
									Icon(
										imageVector = Icons.Default.Delete,
										contentDescription = "Deletar",
										tint = MaterialTheme.colorScheme.error,
										modifier = Modifier.size(AppConstants.FontSize.extraLarge.value.dp)
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

			Spacer(modifier = Modifier.height(AppConstants.Spacing.smallPlus))

			// Informações principais
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.medium),
				verticalAlignment = Alignment.CenterVertically
			) {
				// Data
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.extraSmallPlus)
				) {
					Icon(
						imageVector = Icons.Default.CalendarToday,
						contentDescription = "Data",
						tint = MaterialTheme.colorScheme.primary,
						modifier = Modifier.size(AppConstants.ComponentSize.iconSmall)
					)
					Text(
						text = "${event.date.day}/${event.date.month + 1}",
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Medium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}

				// Horário
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.extraSmallPlus)
				) {
					Icon(
						imageVector = Icons.Default.Schedule,
						contentDescription = "Horário",
						tint = MaterialTheme.colorScheme.primary,
						modifier = Modifier.size(AppConstants.ComponentSize.iconSmall)
					)
					Text(
						text = "${event.hourStart} - ${event.hourEnd}",
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Medium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}

			// Descrição (se houver)
			if (event.description.isNotBlank()) {
				Spacer(modifier = Modifier.height(AppConstants.Spacing.small))
				Text(
					text = event.description,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis
				)
			}

			// Local e Grupo (se houver)
			val hasExtraInfo = event.local.isNotBlank() || event.groups != null
			if (hasExtraInfo) {
				Spacer(modifier = Modifier.height(AppConstants.Spacing.small))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.medium),
					verticalAlignment = Alignment.CenterVertically
				) {
					// Local
					if (event.local.isNotBlank()) {
						Row(
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.extraSmall)
						) {
							Icon(
								imageVector = Icons.Default.LocationOn,
								contentDescription = "Local",
								tint = MaterialTheme.colorScheme.secondary,
								modifier = Modifier.size(AppConstants.ComponentSize.iconExtraSmall)
							)
							Text(
								text = event.local,
								style = MaterialTheme.typography.bodySmall,
								color = MaterialTheme.colorScheme.onSurfaceVariant,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}
					}

					// Grupo
					event.groups?.let { grupo ->
						Row(
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.extraSmall)
						) {
							Icon(
								imageVector = Icons.Default.Groups,
								contentDescription = "Grupo",
								tint = MaterialTheme.colorScheme.secondary,
								modifier = Modifier.size(AppConstants.ComponentSize.iconExtraSmall)
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
				Text("Tem certeza que deseja remover a reunião \"${event.title}\"?")
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