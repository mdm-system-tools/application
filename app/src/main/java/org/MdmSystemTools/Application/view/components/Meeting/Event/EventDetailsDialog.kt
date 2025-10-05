package org.MdmSystemTools.Application.view.components.Meeting.Event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.model.DTO.EventDto
import org.MdmSystemTools.Application.view.theme.AppConstants

@Composable
fun EventDetailsDialog(
    event: EventDto,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.smallPlus)
            ) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = "Evento",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(AppConstants.ComponentSize.iconLarge)
                )
                Text(
                    text = event.title,
                    fontSize = AppConstants.FontSize.title,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(AppConstants.Spacing.medium)
            ) {
                // Descrição
                if (event.description.isNotBlank()) {
                    DetailItem(
                        icon = Icons.Default.Description,
                        label = "Descrição",
                        value = event.description
                    )
                }

                // Data e Horários
                DetailItem(
                    icon = Icons.Default.CalendarToday,
                    label = "Data",
                    value = "${event.date.day}/${event.date.month + 1}/${event.date.year}"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.medium)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        DetailItem(
                            icon = Icons.Default.Schedule,
                            label = "Início",
                            value = event.hourStart,
                            compact = true
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        DetailItem(
                            icon = Icons.Default.Schedule,
                            label = "Fim",
                            value = event.hourEnd,
                            compact = true
                        )
                    }
                }

                // Local
                if (event.local.isNotBlank()) {
                    DetailItem(
                        icon = Icons.Default.LocationOn,
                        label = "Local",
                        value = event.local
                    )
                }

                // Região
                if (event.region.isNotBlank()) {
                    DetailItem(
                        icon = Icons.Default.Map,
                        label = "Região",
                        value = event.region
                    )
                }

                // Projeto
                if (event.project.isNotBlank()) {
                    DetailItem(
                        icon = Icons.Default.Work,
                        label = "Projeto",
                        value = event.project
                    )
                }

                // Grupo
                event.groups?.let { grupo ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.small)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Groups,
                            contentDescription = "Grupo",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(AppConstants.ComponentSize.iconMedium)
                        )
                        Text(
                            text = "Grupo:",
                            fontSize = AppConstants.FontSize.medium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = AppConstants.Alpha.semiTransparent)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.small)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(AppConstants.ComponentSize.iconSmall)
                                    .clip(CircleShape)
                                    .background(grupo.cor)
                            )
                            Text(
                                text = grupo.nome,
                                fontSize = AppConstants.FontSize.medium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        },
        shape = RoundedCornerShape(AppConstants.CornerRadius.large)
    )
}

@Composable
private fun DetailItem(
    icon: ImageVector,
    label: String,
    value: String,
    compact: Boolean = false
) {
    if (compact) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppConstants.Spacing.extraSmall)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.extraSmall)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(AppConstants.ComponentSize.iconSmall)
                )
                Text(
                    text = label,
                    fontSize = AppConstants.FontSize.small,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = AppConstants.Alpha.semiTransparent)
                )
            }
            Text(
                text = value,
                fontSize = AppConstants.FontSize.medium,
                fontWeight = FontWeight.Medium
            )
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.small)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(AppConstants.ComponentSize.iconMedium)
            )
            Column {
                Text(
                    text = label,
                    fontSize = AppConstants.FontSize.small,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = AppConstants.Alpha.semiTransparent)
                )
                Text(
                    text = value,
                    fontSize = AppConstants.FontSize.medium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}