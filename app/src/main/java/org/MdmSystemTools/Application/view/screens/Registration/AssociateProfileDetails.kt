package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssociateProfileDetails(onClickBackScreen: () -> Unit) {
	TopAppBar(
		title = {
			Text("Associate Profile")
		},
		actions = {
			IconButton(
				onClick = onClickBackScreen,
			) {
				Icon(
					Icons.AutoMirrored.Filled.ArrowBack,
					contentDescription = null,
					tint = MaterialTheme.colorScheme.onSurfaceVariant,
				)
			}
		},
	)
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Text("TODO Implementar uma tela")
	}
}