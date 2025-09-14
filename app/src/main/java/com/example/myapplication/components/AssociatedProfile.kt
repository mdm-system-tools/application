package com.example.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.model.AssociatedDto

@Composable
fun AssociatedProfile(associated: AssociatedDto) {
  Row(
    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
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
          fontSize = 12.sp,
          fontWeight = FontWeight(400)
        )
      }
    }
  }
}
