package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.entity.Associate
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.view.theme.AppConstants

@Composable
fun Profile(
	projeto: Project,
	onClick: (Project) -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 6.dp)
			.clickable { onClick(projeto) },
		shape = RoundedCornerShape(8.dp),
		colors = CardDefaults.cardColors(containerColor = Color.White)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(12.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Surface(
				shape = CircleShape,
				color = Color(0xFFE6D9FF),
				modifier = Modifier.size(40.dp)
			) {
				Box(contentAlignment = Alignment.Center) {
					Text(
						text = projeto.id.toString(),
						color = Color(0xFF8A4FFF),
						fontWeight = FontWeight.Bold
					)
				}
			}

			Column(
				modifier = Modifier.weight(1f)
			) {
				Text(
					text = projeto.name,
					fontWeight = FontWeight.SemiBold,
					fontSize = 14.sp
				)
				Text(
					text = "Região: ${projeto.region}",
					fontSize = 12.sp,
					color = Color.Gray
				)
				Text(
					text = "Valor: R$ ${projeto.value}",
					fontSize = 12.sp,
					color = Color.Gray
				)
			}

			Icon(
				imageVector = Icons.Default.ChevronRight,
				contentDescription = null,
				modifier = Modifier.size(20.dp),
				tint = Color.DarkGray
			)
		}
	}
}

@Composable
fun Profile(associate: Associate, onClick: () -> Unit) {
	Card(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
		) {
			Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
				ImageProfile()
				InformationToAssociate(associate)
			}
			Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
		}
	}
}

@Composable
fun Profile(group: Grupo, onClick: () -> Unit) {
	Card(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
		) {
			Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
				ImageProfile()
				Text(group.schedule)
			}
			Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
		}
	}
}

@Composable
private fun ImageProfile() {
	Image(
		painter = painterResource(id = R.drawable.ic_launcher_background),
		contentDescription = null,
		modifier = Modifier
			.padding(all = 6.dp)
			.size(32.dp)
			.clip(CircleShape),
	)
}

@Composable
private fun InformationToAssociate(associate: Associate) {
	Column(Modifier.padding(start = 8.dp)) {
		Text(
			associate.name.toString(),
			fontSize = AppConstants.FontSize.medium,
			fontWeight = FontWeight(500),
			maxLines = 1,
		)
		Row {
			Text(
				"Grupo: ${associate.groupId} Carterinha: ${associate.numberCard}",
				fontSize = AppConstants.FontSize.small,
				fontWeight = FontWeight(400),
			)
		}
	}
}