package org.MdmSystemTools.Application.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import java.util.Calendar


object TopBarFactory {
	@Composable
	fun make(navDestination: NavDestination?): @Composable (() -> Unit) {
		return when (navDestination?.route) {
			Screen.Associate.route -> {
				{ ToAssociateScreen() }
			}

			Screen.Calendar.route -> {
				{ ToCalendarScreen() }
			}

			else -> {
				{ DefaultTopBar() }
			}
		}
	}

	@Composable
	fun DefaultTopBar() {
		return Unit
	}

	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	private fun ToCalendarScreen() {
		val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
		val currentYear = Calendar.getInstance().get(Calendar.YEAR)

		val monthNames = listOf(
			"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
			"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
		)

		val nameMonth = monthNames[currentMonth]

		TopAppBar(
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = MaterialTheme.colorScheme.primaryContainer,
				titleContentColor = MaterialTheme.colorScheme.primary,
			),
			title = {
				Text(
					text = "$nameMonth $currentYear",
					style = MaterialTheme.typography.headlineMedium,
					fontWeight = FontWeight.Bold,
					textAlign = TextAlign.Center,
					color = MaterialTheme.colorScheme.onBackground
				)
			}
		)
	}

	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	private fun ToAssociateScreen() {
		var query by remember { mutableStateOf("") }
		var active by remember { mutableStateOf(false) }
		val colors1 = SearchBarDefaults.colors()

		SearchBar(
			inputField = {
				SearchBarDefaults.InputField(
					query = query,
					onQueryChange = { query = it },
					onSearch = { /* Ação ao pesquisar */ },
					expanded = active,
					onExpandedChange = {},
					placeholder = { Text("Pesquisar...") },
					leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
					trailingIcon = {
						if (active) {
							IconButton(onClick = {
								if (query.isNotEmpty()) {
									query = ""
								} else {
									active = false
								}
							}) {
								Icon(Icons.Default.Close, contentDescription = "Fechar")
							}
						}
					},
					colors = colors1.inputFieldColors,
				)
			},
			expanded = active,
			onExpandedChange = {},
			modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
			shape = SearchBarDefaults.inputFieldShape,
			colors = colors1,
			tonalElevation = SearchBarDefaults.TonalElevation,
			shadowElevation = SearchBarDefaults.ShadowElevation,
			windowInsets = SearchBarDefaults.windowInsets,
			content = {
				Text("Sugestão 1")
				Text("Sugestão 2")
			},
		)
	}
}