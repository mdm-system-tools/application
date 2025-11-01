package org.MdmSystemTools.Application.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import org.MdmSystemTools.Application.view.theme.AppConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerTitle(title: Int, subtitle: Int, showBackIcon: Boolean = false, onClick: () -> Unit = {}) {
  TopAppBar(
    colors =
      TopAppBarDefaults.topAppBarColors(
        containerColor = Color(0xFF1C6AEA),
        titleContentColor = Color.White,
      ),
    title = {
      Column {
        Text(
          stringResource(title),
          fontSize = AppConstants.FontSize.large,
          fontWeight = FontWeight(500),
        )
        Text(
          stringResource(subtitle),
          fontSize = AppConstants.FontSize.medium,
          fontWeight = FontWeight(400),
        )
      }
    },
    navigationIcon = {
      if (showBackIcon) {
        IconButton(onClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
      }
    },
  )
}
