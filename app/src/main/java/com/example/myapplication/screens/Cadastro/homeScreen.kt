package org.mdmsystemtools.application.presentation.ui.screens.Cadastro

import android.R.attr.onClick
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.components.AssociatedProfile
import com.example.myapplication.viewmodel.ListAssociatedViewModel
import org.mdmsystemtools.application.presentation.ui.components.ButtonFormAdd
import org.mdmsystemtools.application.presentation.ui.components.QuickAccessButtons
import org.mdmsystemtools.application.presentation.ui.components.SearchBar

@Composable
fun CadastrosScreen(
  navController: NavHostController,
  viewModel: ListAssociatedViewModel = hiltViewModel(),
) {
  val listAssociateds by viewModel.listAssociateds.collectAsState()

  Surface {
    ButtonFormAdd(onClick = {
      navController.navigate("Formulario")
    })

    Column {
      SearchBar(
        placeholder = "Pesquisar cadastros...", onSearchChange = { searchText ->
        })

      QuickAccessButtons(
        onLastMeetingClick = { /* TODO */ },
        onLastSearchClick = { /* TODO */ },
      )

      LazyColumn(
        modifier = Modifier
          .padding(
            start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp
          )
      ) {
        items(listAssociateds) { associated ->
          AssociatedProfile(associated)
        }
      }
    }
  }
}
