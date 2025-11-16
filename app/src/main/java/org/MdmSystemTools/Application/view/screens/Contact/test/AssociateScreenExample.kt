package org.MdmSystemTools.Application.view.screens.Contact.associate

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

/**
 * Example of how to use ListScreenBuilder for Associates
 *
 * Example data class (you can use the real entity)
 */
data class AssociateExample(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)

/**
 * Example implementation of Associates screen using ListScreenBuilder
 */
@Composable
fun AssociateScreenExample(
    onAssociateClick: (AssociateExample) -> Unit = {}
) {
    val fakeAssociates = listOf(
        AssociateExample(1, "João Silva", "joao@email.com", "(11) 98765-4321"),
        AssociateExample(2, "Maria Santos", "maria@email.com", "(11) 91234-5678"),
        AssociateExample(3, "Pedro Costa", "pedro@email.com", "(11) 99876-5432"),
    )

    ListScreenBuilder(
        items = fakeAssociates,
        searchPlaceholder = "Search Associate",
        onAddClick = { /* TODO: Add new associate */ },
        itemContent = { associate, onItemClick ->
            AssociateCard(associate = associate, onClick = onItemClick)
        },
        onItemClick = onAssociateClick
    )
}

@Composable
fun AssociateCard(
    associate: AssociateExample,
    onClick: (AssociateExample) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(associate) },
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
            // AVATAR COM INICIAL
            Surface(
                shape = CircleShape,
                color = Color(0xFFDFDFDF),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = associate.name.first().toString(),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            // INFORMAÇÕES
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = associate.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = associate.email,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = associate.phone,
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
