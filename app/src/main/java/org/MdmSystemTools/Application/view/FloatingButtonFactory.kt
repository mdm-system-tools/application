package org.MdmSystemTools.Application.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import org.MdmSystemTools.Application.navigation.Route

object FloatingButtonFactory {
	fun make(
		navDestination: NavDestination?, onClick: () -> Unit
	): @Composable (() -> Unit) {
		return when (navDestination?.route) {
			Route.Calendar.destination -> {
				{ ToCalendar(onClick) }
			}

			else -> {
				{ delete() }
			}
		}
	}

	fun delete() {
		return Unit
	}

	@Composable
	fun ToCalendar(onClick: () -> Unit) {
		FloatingActionButton(
			onClick = onClick,
			shape = CircleShape,
			containerColor = MaterialTheme.colorScheme.primary,
		) {
			Text(
				text = "+", fontSize = 28.sp, textAlign = TextAlign.Center
			)
		}
	}

}