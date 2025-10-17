package org.MdmSystemTools.Application.view.components.Registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
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
		modifier = Modifier
      .height(64.dp)
      .width(270.dp), onClick = onClick
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
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

	}
}

@Preview(showBackground = true)
@Composable
private fun AssociateProfilePreview() {
	val assoc = AssociateDto(1, 1, "João da Silva Pereira")
	AssociateProfile(assoc, {})
}