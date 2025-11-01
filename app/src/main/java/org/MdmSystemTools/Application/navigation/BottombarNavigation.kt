package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.R

@Serializable
sealed interface Route {
  @Serializable data object AssociateList : Route

  @Serializable data object AssociateDashboard : Route

  @Serializable data object Collaboration : Route

  @Serializable data object Calendar : Route

  @Serializable data object AssociateForm : Route

  @Serializable data object EventForm : Route
}

@Serializable data class TopLevelDestination(val icon: Int, val label: Int)

val TOP_LEVEL_DESTINATIONS =
  listOf(
    TopLevelDestination(icon = R.drawable.ic_associate, label = R.string.label_associate) to
      Route.AssociateDashboard,
    TopLevelDestination(icon = R.drawable.ic_associate, label = R.string.label_collaboration) to
      Route.Collaboration,
    TopLevelDestination(icon = R.drawable.ic_associate, label = R.string.label_calendar) to
      Route.Calendar,
  )

class NavigationActions(private val navController: NavHostController) {
  fun navigateTo(destination: Route) {
    navController.navigate(destination) {
      popUpTo(navController.graph.findStartDestination().id) { saveState = true }
      launchSingleTop = true
      restoreState = true
    }
  }
}
