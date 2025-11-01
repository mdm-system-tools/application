package org.MdmSystemTools.Application.view.components.Common

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.model.dto.AssociateDto
import org.MdmSystemTools.Application.view.theme.AppConstants

@Composable
fun AssociateProfile(associated: AssociateDto, onClick: () -> Unit) {
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
				InformationToAssociate(associated)
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

@Preview(showBackground = true)
@Composable
private fun AssociateProfilePreview() {
	val assoc = AssociateDto("João da Silva Pereira", 1, 1)
	AssociateProfile(assoc, {})
}