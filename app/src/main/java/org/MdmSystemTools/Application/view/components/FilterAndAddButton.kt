package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.view.screens.Contact.TabsForContact

@Composable
fun FilterAndAddButton(selectedTab: TabsForContact, onClickAdd: (TabsForContact) -> Unit) {
	//TODO implementar SearchBar
	Row(
		modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 0.dp)
      .padding(top = 12.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(6.dp)
		) {
			//TODO implementar filtro
			Icon(
				imageVector = Icons.Default.Tune,
				contentDescription = "Filter",
				modifier = Modifier.size(20.dp)
			)
			Text("Filter")
		}

		Button(
			onClick = { onClickAdd(selectedTab) },
			modifier = Modifier
        .width(140.dp)
        .height(32.dp),
			shape = RoundedCornerShape(8.dp)
		) {
			Icon(
				imageVector = Icons.Default.Add,
				contentDescription = "Add",
				modifier = Modifier
          .size(16.dp)
          .padding(end = 4.dp)
			)
			Text("Add")
		}
	}
}
