package org.mdmsystemtools.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.mdmsystemtools.application.data.model.AssociatedDto
import org.mdmsystemtools.application.presentation.ui.screens.telaPrincipal.TelaPrincipal


class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MaterialTheme {
				Surface {
					CadastrosViewModel()
				}
			}
		}
	}
}

@Composable
fun CadastrosViewModel() {
	Surface() {
		Column(
			Modifier
                .padding(start = 8.dp, top = 40.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(
                    ScrollState(0)
                ),
			verticalArrangement = Arrangement.spacedBy(16.dp),

			) {
			Text("hello")
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
			AssociatedProfile()
		}

	}
}

@Preview(showBackground = true)
@Composable
private fun CadastrosViewModelPreview() {
	CadastrosViewModel()
}

@Composable
private fun AssociatedProfile() {
	val associated = AssociatedDto(10000, 10000, "Jose Maria")
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
            .height(64.dp)
            .width(270.dp)
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_launcher_background),
			contentDescription = null,
			modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
		)
		Column(Modifier.padding(start = 8.dp)) {
			Text(
				LoremIpsum(50).values.first(),
				fontSize = 18.sp,
				fontWeight = FontWeight(500),
				maxLines = 2
			)
			Row {
				Text(
					"Grupo: ${associated.groupId} Carterinha: ${associated.numberCard}",
					fontSize = 12.sp, fontWeight = FontWeight(400)
				)
			}
		}
	}
}

@Composable
fun App() {
	val navController = rememberNavController()
	TelaPrincipal(navController = navController)
}