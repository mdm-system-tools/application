package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorPicker(
	selectedColor: Color,
	onColorSelected: (Color) -> Unit,
	modifier: Modifier = Modifier
) {
	val colors = listOf(
		Color(0xFF4CAF50), // Verde
		Color(0xFF2196F3), // Azul
		Color(0xFFFF9800), // Laranja
		Color(0xFF9C27B0), // Roxo
		Color(0xFFF44336), // Vermelho
		Color(0xFF009688), // Teal
		Color(0xFFFF5722), // Deep Orange
		Color(0xFF795548)  // Marrom
	)

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
			Text(
				text = "Cor do evento",
				fontSize = 14.sp,
				fontWeight = FontWeight.Medium,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
				modifier = Modifier.padding(bottom = 12.dp)
			)

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(12.dp)
			) {
				colors.forEach { color ->
					Box(
						modifier = Modifier
							.size(40.dp)
							.clip(RoundedCornerShape(10.dp))
							.background(color)
							.clickable { onColorSelected(color) },
						contentAlignment = Alignment.Center
					) {
						if (color == selectedColor) {
							Icon(
								Icons.Default.Check,
								contentDescription = "Selecionado",
								tint = Color.White,
								modifier = Modifier.size(20.dp)
							)
						}
					}
				}
			}
		}
	}
}