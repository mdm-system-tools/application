package org.mdmsystemtools.application.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonFormAdd(modifier: Modifier, onClick: () -> Unit) {
	val windowInfo = LocalWindowInfo.current
	val density = LocalDensity.current
	val screenHeight = with(density) { windowInfo.containerSize.height.toDp() }
	val dynamicOffset = screenHeight * 0.27f
	
	Box(modifier.fillMaxSize()) {
		FloatingActionButton(
			onClick = onClick,
			shape = CircleShape,
			containerColor = MaterialTheme.colorScheme.primary,
			modifier = Modifier
				.align(Alignment.CenterEnd)
				.padding(end = 16.dp)
				.offset(y = dynamicOffset)
		) {
			Text(
				text = "+",
				fontSize = 28.sp,
				textAlign = TextAlign.Center
			)
		}
	}
}