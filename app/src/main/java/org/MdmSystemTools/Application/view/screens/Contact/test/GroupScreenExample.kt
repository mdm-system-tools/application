package org.MdmSystemTools.Application.view.screens.Contact.group

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import org.MdmSystemTools.Application.view.components.generic.ListScreenBuilder


data class GroupExample(
    val id: Int,
    val name: String,
    val description: String,
    val memberCount: Int
)

@Composable
fun GroupScreenExample(
    onGroupClick: (GroupExample) -> Unit = {}
) {
    val fakeGroups = listOf(
        GroupExample(1, "Work Group", "Alpha project team", 5),
        GroupExample(2, "Administrative", "Company administrative staff", 8),
        GroupExample(3, "Finance", "Finance department", 3),
    )

    ListScreenBuilder(
        items = fakeGroups,
        searchPlaceholder = "Search Group",
        onAddClick = { /* TODO: Add new group */ },
        itemContent = { group, onItemClick ->
            GroupCard(group = group, onClick = onItemClick)
        },
        onItemClick = onGroupClick
    )
}

@Composable
fun GroupCard(
    group: GroupExample,
    onClick: (GroupExample) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(group) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
        ) {
            // ÍCONE COM ID
            Surface(
                shape = CircleShape,
                color = Color(0xFFE3F2FD),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "G${group.id}",
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            // INFORMAÇÕES
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = group.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = group.description,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${group.memberCount} membro${if (group.memberCount > 1) "s" else ""}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Seta
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.DarkGray
            )
        }
    }
}
