package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SaveBottomBar(
	onSave: () -> Unit,
	canSave: Boolean,
	saveText: String = "Salvar evento"
) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		),
		elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
	) {
		Button(
			onClick = onSave,
			enabled = canSave,
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
				.height(56.dp),
			shape = RoundedCornerShape(12.dp)
		) {
			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					Icons.Default.Check,
					contentDescription = "Salvar",
					modifier = Modifier.size(20.dp)
				)
				Text(
					text = saveText,
					fontSize = 16.sp,
					fontWeight = FontWeight.SemiBold
				)
			}
		}
	}
}