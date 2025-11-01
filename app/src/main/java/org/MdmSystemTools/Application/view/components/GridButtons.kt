package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> GridButtons(items: List<T>, columns: Int = 2, itemContent: @Composable (T) -> Unit) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(columns),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(24.dp),
    modifier = Modifier.height(200.dp),
  ) {
    items(items) { item -> itemContent(item) }
  }
}
