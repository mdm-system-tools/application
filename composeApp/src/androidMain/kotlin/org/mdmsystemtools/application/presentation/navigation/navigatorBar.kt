import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.mdmsystemtools.application.presentation.ui.screens.Cadastro.CadastrosScreen
import org.mdmsystemtools.application.presentation.ui.screens.Cadastro.Formulario
import org.mdmsystemtools.application.presentation.ui.screens.Camera.CameraScreen
import org.mdmsystemtools.application.presentation.ui.screens.Reunião.ReuniaoScreen

enum class Destination(
  val route: String,
  val label: String,
  val icon: ImageVector,
  val contentDescription: String
) {
  CADASTROS("Cadastros", "Cadastros", Icons.Default.People, "Cadastros"),
  CAMERA("Camera", "Camera", Icons.Default.CameraAlt, "Camera"),
  REUNIAO("Reuniao", "Reuniao", Icons.Default.CalendarToday, "Reuniao"),
}

@Composable
fun AppNavHost(
  navController: NavHostController,
  startDestination: Destination,
  modifier: Modifier = Modifier
) {
  NavHost(
    navController,
    startDestination = startDestination.route
  ) {
    Destination.entries.forEach { destination ->
      composable(destination.route) {
        when (destination) {
          Destination.CADASTROS -> CadastrosScreen(navController)
          Destination.CAMERA -> CameraScreen()
          Destination.REUNIAO -> ReuniaoScreen()
        }
      }
    }
		composable("Formulario") { Formulario(modifier) }
  }
}
