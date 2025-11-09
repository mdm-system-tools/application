package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.R

@Serializable
sealed class BottomNav(val icon: Int, val label: Int) {
  @Serializable
  data object Menu : BottomNav(icon = R.drawable.ic_associate, label = R.string.label_menu)

  @Serializable
  data object Contact : BottomNav(icon = R.drawable.ic_associate, label = R.string.label_contact)

  @Serializable
  data object Agenda : BottomNav(icon = R.drawable.ic_associate, label = R.string.label_agenda)
}

val TOP_LEVEL_DESTINATIONS = listOf(BottomNav.Menu, BottomNav.Contact, BottomNav.Agenda)

class NavigationActions(private val navController: NavHostController) {
  fun navigateTo(destination: BottomNav) {
    navController.navigate(destination) {
      popUpTo(navController.graph.findStartDestination().id) { saveState = true }
      launchSingleTop = true
      restoreState = true
    }
  }
}
