package org.mdmsystemtools.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mdmsystemtools.application.presentation.ui.screens.telaPrincipal.TelaPrincipal


class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		enableEdgeToEdge()
		super.onCreate(savedInstanceState)

		setContent {
			App()
		}
	}
}

@Composable
@Preview
fun App() {
	MaterialTheme {
		val navController = rememberNavController()
		TelaPrincipal(navController = navController)
	}
}