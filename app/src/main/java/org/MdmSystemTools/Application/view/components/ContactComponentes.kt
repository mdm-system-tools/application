package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.view.screens.Contact.ContactUiModel
import org.MdmSystemTools.Application.view.theme.AppConstants

@Composable
fun Profile(model: ContactUiModel, onClick: () -> Unit) {
	when (model) {
		is ContactUiModel.Associate -> Profile(model, onClick)
		is ContactUiModel.Group -> Profile(model, onClick)
	}
}

@Composable
fun Profile(associate: AssociateDto, onClick: () -> Unit) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		onClick = onClick
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Row(
				modifier = Modifier,
				verticalAlignment = Alignment.CenterVertically,
			) {
				ImageProfile()
				InformationToAssociate(associate)
			}
			Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
		}
	}
}

@Composable
fun Profile(group: GroupDto, onClick: () -> Unit) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		onClick = onClick
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Row(
				modifier = Modifier,
				verticalAlignment = Alignment.CenterVertically,
			) {
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
      .clip(CircleShape)
	)
}

@Composable
private fun InformationToAssociate(associated: AssociateDto) {
	Column(Modifier.padding(start = 8.dp)) {
		Text(
			associated.name,
			fontSize = AppConstants.FontSize.medium,
			fontWeight = FontWeight(500),
			maxLines = 1
		)
		Row {
			Text(
				"Grupo: ${associated.groupId} Carterinha: ${associated.numberCard}",
				fontSize = AppConstants.FontSize.small,
				fontWeight = FontWeight(400)
			)
		}
	}
}