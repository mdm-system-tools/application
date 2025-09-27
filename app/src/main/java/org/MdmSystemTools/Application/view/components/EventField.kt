package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventField(
	label: String,
	value: String,
	onValueChange: (String) -> Unit,
	icon: ImageVector,
	placeholder: String,
	singleLine: Boolean = true,
	minLines: Int = 1,
	modifier: Modifier = Modifier
) {
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
					imageVector = icon,
					contentDescription = label,
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier.size(20.dp)
				)
				Text(
					text = label,
					fontSize = 14.sp,
					fontWeight = FontWeight.Medium,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
				)
			}

			Spacer(modifier = Modifier.height(8.dp))

			TextField(
				value = value,
				onValueChange = onValueChange,
				placeholder = {
					Text(
						text = placeholder,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
					)
				},
				singleLine = singleLine,
				minLines = minLines,
				colors = TextFieldDefaults.colors(
					focusedContainerColor = Color.Transparent,
					unfocusedContainerColor = Color.Transparent,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent
				),
				modifier = Modifier.fillMaxWidth()
			)
		}
	}
}