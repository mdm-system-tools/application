package org.MdmSystemTools.Application

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.MdmSystemTools.Application.navigation.BottomNav
import org.MdmSystemTools.Application.navigation.navigateToLogin
import org.MdmSystemTools.Application.navigation.navigateToRegister
import org.MdmSystemTools.Application.navigation.associateForm
import org.MdmSystemTools.Application.navigation.associateProfileDetails
import org.MdmSystemTools.Application.navigation.calendar
import org.MdmSystemTools.Application.navigation.contact
import org.MdmSystemTools.Application.navigation.criarNovoProjeto
import org.MdmSystemTools.Application.navigation.dashboard
import org.MdmSystemTools.Application.navigation.eventForm
import org.MdmSystemTools.Application.navigation.eventProfileDetails
import org.MdmSystemTools.Application.navigation.groupForm
import org.MdmSystemTools.Application.navigation.login
import org.MdmSystemTools.Application.navigation.meetingHistory
import org.MdmSystemTools.Application.navigation.meetingRollCall
import org.MdmSystemTools.Application.navigation.navigateToContact
import org.MdmSystemTools.Application.navigation.navigateToDashboard
import org.MdmSystemTools.Application.navigation.navigateToDetailsByTab
import org.MdmSystemTools.Application.navigation.navigateToEventForm
import org.MdmSystemTools.Application.navigation.navigateToEventProfileDetails
import org.MdmSystemTools.Application.navigation.navigateToMeetingRollCall
import org.MdmSystemTools.Application.navigation.register

@Composable
fun App() {
  val navController = rememberNavController()

  Surface { AppNavHost(navController) }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun AppNavHost(navController: NavHostController) {
  LaunchedEffect(navController) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
      Log.d("Navigation", "navigation to ${destination.route}")
    }
  }

  NavHost(navController = navController, startDestination = BottomNav.Menu) {
    contact(
      onBack = { navController.popBackStack() },
      onClickItem = { id, tab -> navController.navigateToDetailsByTab(id, tab) },
      onClickAdd = { tab -> navController.navigateToDetailsByTab(tab) },
    )

    associateProfileDetails(onClickBackScreen = { navController.popBackStack() })

    associateForm(
      onClickBackScreen = { navController.popBackStack() },
      onClickConfirmButton = { navController.popBackStack() },
    )

    groupForm(
      onClickBackScreen = { navController.popBackStack() },
      onClickConfirmButton = { navController.popBackStack() },
    )

    criarNovoProjeto(
      onClickBackScreen = { navController.popBackStack() },
      onClickConfirmButton = { navController.popBackStack() },
    )

    dashboard(
      onClickMeetingBotton = { tabs -> navController.navigateToMeetingRollCall(tabs) },
      onClickListRegisters = { navController.navigateToContact() },
      onClickListEmployee = { navController.navigateToContact() },
    )

    meetingHistory(onClickBack = { navController.popBackStack() }, onClick = {})

    meetingRollCall(onClickBack = { navController.popBackStack() })

    login(
      onNavigateToDashboard = { navController.navigateToDashboard() },
      onNavigateToRegister = { navController.navigateToRegister() },
    )

    calendar(
      onClickEventProfile = { id -> navController.navigateToEventProfileDetails(id) },
      onClickFloatingButton = { navController.navigateToEventForm() },
    )

    eventProfileDetails(onClickBackScreen = { navController.popBackStack() })

    eventForm(
      onClickBackScreen = { navController.popBackStack() },
      onClickConfirmButton = { navController.popBackStack() },
    )

    register(
      onNavigateToLogin = { navController.navigateToLogin() },
      onRegisterSuccess = { navController.navigateToDashboard() }
    )
  }
}
