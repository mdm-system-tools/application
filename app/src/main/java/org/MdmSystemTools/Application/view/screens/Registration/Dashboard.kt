package org.MdmSystemTools.Application.view.screens.Registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.MdmSystemTools.Application.view.theme.AppConstants


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Dashboard(onClick: () -> Unit) {
	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color(0xFF1C6AEA),
					titleContentColor = Color.White
				),
				title = {
					Column {
						Text(
							"Associate",
							fontSize = AppConstants.FontSize.large,
							fontWeight = FontWeight(500)
						)
						Text(
							"Gerencie Associados, Groupos e Projetos",
							fontSize = AppConstants.FontSize.medium,
							fontWeight = FontWeight(400)
						)
					}
				}
			)
		}
	) { paddingValues ->
		Column(Modifier.padding(paddingValues)) {
			Button(onClick) {
				Text("Associado")
			}
		}
	}
}