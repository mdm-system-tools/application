package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun <T> GenericList(
  items: List<T>,
  onClickItem: (Int) -> Unit,
  itemContent: @Composable (T, Int, (Int) -> Unit) -> Unit,
) {
  Column {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      itemsIndexed(items) { index, item -> itemContent(item, index) { onClickItem(index) } }
    }
  }
}
