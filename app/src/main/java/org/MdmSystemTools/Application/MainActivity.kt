package org.MdmSystemTools.Application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import dagger.hilt.android.AndroidEntryPoint
import org.MdmSystemTools.Application.view.App

//TODO separar a estrutura de navegação
//TODO remover o botão da tela
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MaterialTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					shape = RectangleShape
				) {
					App()
				}
			}
		}
	}
}