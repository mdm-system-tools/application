package org.mdmsystemtools.application.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonFormAdd(modifier: Modifier, onClick: () -> Unit) {
	Box(modifier) {
		Button(
			onClick = onClick,
			shape = CircleShape,
			modifier = Modifier
				.size(70.dp)
				.align(Alignment.Center)
				//.offset(x = (-16).dp, y = 275.dp)
		) {
			Text(
				text = "+",
				fontSize = 28.sp,
				textAlign = TextAlign.Center
			)
		}
	}
}