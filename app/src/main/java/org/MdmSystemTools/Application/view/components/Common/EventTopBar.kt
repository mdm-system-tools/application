package org.MdmSystemTools.Application.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTopBar(
	title: String = "Novo evento",
	onNavigateBack: () -> Unit
) {
	TopAppBar(
		title = {
			Text(
				text = title,
				fontSize = 20.sp,
				fontWeight = FontWeight.SemiBold
			)
		},
		navigationIcon = {
			IconButton(onClick = onNavigateBack) {
				Icon(
					Icons.AutoMirrored.Filled.ArrowBack,
					contentDescription = "Voltar"
				)
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.surface
		)
	)
}