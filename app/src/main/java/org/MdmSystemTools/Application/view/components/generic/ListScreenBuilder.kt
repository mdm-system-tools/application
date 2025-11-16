package org.MdmSystemTools.Application.view.components.generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Generic reusable list screen builder that can be used for any list of items.
 * Supports Projects, Associates, Groups, etc.
 *
 * @param T Generic type of items in the list
 * @param items List of items to display
 * @param searchPlaceholder Placeholder text for search field
 * @param onAddClick Callback when add button is clicked
 * @param onFilterClick Callback when filter button is clicked
 * @param itemContent Composable that renders each item in the list
 * @param onItemClick Callback when an item is clicked
 *
 * Example usage:
 * ```
 * ListScreenBuilder(
 *     items = myItems,
 *     searchPlaceholder = "Search Projects",
 *     onAddClick = { /* Handle add */ },
 *     itemContent = { item, onClick ->
 *         MyItemCard(item = item, onClick = onClick)
 *     },
 *     onItemClick = { selectedItem ->
 *         // Handle item selection
 *     }
 * )
 * ```
 */
@Composable
fun <T> ListScreenBuilder(
    items: List<T>,
    searchPlaceholder: String = "Search...",
    onAddClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    itemContent: @Composable (item: T, onItemClick: (T) -> Unit) -> Unit,
    onItemClick: (T) -> Unit = {}
) {
    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text(searchPlaceholder) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )

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
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Filter",
                    modifier = Modifier.size(20.dp)
                )
                Text("Filter")
            }

            Button(
                onClick = onAddClick,
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(items) { item ->
                itemContent(item, onItemClick)
            }
        }
    }
}
