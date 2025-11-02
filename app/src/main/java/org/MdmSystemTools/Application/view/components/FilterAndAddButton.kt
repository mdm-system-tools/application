package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import org.MdmSystemTools.Application.R
import org.MdmSystemTools.Application.view.screens.Contact.Tabs

@Composable
fun FilterAndAddButton(selectedTab: Tabs, onClickAdd: (Tabs) -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    // TODO Implementar Barra de pequisa
    Card(
      // TODO Implementar uma filtro
      {},
      colors =
        CardDefaults.cardColors(
          containerColor = Color.Transparent,
          contentColor = colorResource(R.color.cinza2),
        ),
    ) {
      Icon(Icons.Default.FilterAlt, null)
      Text("Filtros")
    }
    Card(
      { onClickAdd(selectedTab) },
      colors =
        CardDefaults.cardColors(
          containerColor = colorResource(R.color.azul),
          contentColor = Color.White,
        ),
    ) {
      Icon(Icons.Default.Add, null)
      Text("Adicionar")
    }
  }
}
