package org.MdmSystemTools.Application.view.components.Meeting.Calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.view.theme.AppConstants

@Composable
fun MonthTitle(
	month: Int,
	year: Int,
	modifier: Modifier = Modifier
) {
	Text(
		text = "${getMonthName(month)} $year",
		style = MaterialTheme.typography.headlineMedium,
		fontWeight = FontWeight.Bold,
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = AppConstants.Spacing.large, vertical = AppConstants.Spacing.medium),
		textAlign = TextAlign.Center,
		color = MaterialTheme.colorScheme.onBackground
	)
}