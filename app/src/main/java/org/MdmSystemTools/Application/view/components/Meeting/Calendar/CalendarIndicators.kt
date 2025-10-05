package org.MdmSystemTools.Application.view.components.Meeting.Calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.MdmSystemTools.Application.view.theme.AppConstants


@Composable
internal fun EventIndicators(
	eventCount: Int,
	isSelected: Boolean,
	modifier: Modifier = Modifier
) {
	val indicatorColor = if (isSelected) {
		MaterialTheme.colorScheme.onPrimary
	} else {
		MaterialTheme.colorScheme.primary
	}

	Row(
		horizontalArrangement = Arrangement.spacedBy(AppConstants.Spacing.extraSmall / 2),
		modifier = modifier
	) {

		val maxDots = AppConstants.Calendar.maxEventsToShow
		repeat(minOf(eventCount, maxDots)) {
			Box(
				modifier = Modifier
					.size(AppConstants.ComponentSize.eventIndicatorSize)
					.background(indicatorColor, CircleShape)
			)
		}

		if (eventCount > maxDots) {
			Text(
				text = "+",
				fontSize = AppConstants.FontSize.tiny,
				color = indicatorColor,
				fontWeight = FontWeight.Bold
			)
		}
	}
}
