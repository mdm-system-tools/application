package org.MdmSystemTools.Application.view.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.DTO.EventDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
	evento: EventDto,
	onDelete: () -> Unit,
	modifier: Modifier = Modifier
) {
	var isDeleting by remember { mutableStateOf(false) }
	val offsetX = remember { Animatable(0f) }
	val scope = rememberCoroutineScope()

	Card(
		modifier = modifier
			.fillMaxWidth()
			.offset { IntOffset(offsetX.value.toInt(), 0) }
			.pointerInput(Unit) {
				detectDragGestures(
					onDragEnd = {
						if (offsetX.value < -200f) {
							isDeleting = true
							onDelete()
						} else {
							// Voltar à posição original
							scope.launch {
								offsetX.animateTo(0f, animationSpec = spring())
							}
						}
					}
				) { _, dragAmount ->
					scope.launch {
						val newValue = offsetX.value + dragAmount.x
						offsetX.snapTo(newValue.coerceAtMost(0f))
					}
				}
			},
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
		colors = CardDefaults.cardColors(
			containerColor = if (isDeleting) MaterialTheme.colorScheme.errorContainer
			else MaterialTheme.colorScheme.surfaceVariant
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Column(
				modifier = Modifier.weight(1f)
			) {
				Text(
					text = evento.titulo,
					style = MaterialTheme.typography.titleSmall,
					fontWeight = FontWeight.Medium
				)
				Text(
					text = "${evento.data.day}/${evento.data.month + 1}/${evento.data.year}",
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				if (evento.descricao.isNotBlank()) {
					Text(
						text = evento.descricao,
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						modifier = Modifier.padding(top = 4.dp)
					)
				}
			}

			// Botão de delete visível quando arrastando
			AnimatedVisibility(
				visible = offsetX.value < -50f,
				enter = fadeIn() + scaleIn(),
				exit = fadeOut() + scaleOut()
			) {
				IconButton(
					onClick = onDelete,
					modifier = Modifier
						.size(40.dp)
						.clip(CircleShape)
						.background(MaterialTheme.colorScheme.error)
				) {
					Icon(
						imageVector = Icons.Default.Delete,
						contentDescription = "Deletar evento",
						tint = MaterialTheme.colorScheme.onError
					)
				}
			}
		}
	}
}