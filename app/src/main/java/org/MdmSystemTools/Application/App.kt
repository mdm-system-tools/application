package org.MdmSystemTools.Application

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.MdmSystemTools.Application.navigation.BottomNav
import org.MdmSystemTools.Application.navigation.associateForm
import org.MdmSystemTools.Application.navigation.associateDetails
import org.MdmSystemTools.Application.navigation.contact
import org.MdmSystemTools.Application.navigation.projectForm
import org.MdmSystemTools.Application.navigation.dashboard
import org.MdmSystemTools.Application.navigation.groupForm
import org.MdmSystemTools.Application.navigation.login
import org.MdmSystemTools.Application.navigation.navigateToContact
import org.MdmSystemTools.Application.navigation.navigateToDashboard
import org.MdmSystemTools.Application.navigation.navigateToDetailsByTab
import org.MdmSystemTools.Application.navigation.navigateToLogin
import org.MdmSystemTools.Application.navigation.navigateToRegister
import org.MdmSystemTools.Application.navigation.navigateToProjectDetails
import org.MdmSystemTools.Application.navigation.register
import org.MdmSystemTools.Application.view.screens.Meeting.meetingRollCall
import org.MdmSystemTools.Application.view.screens.Meeting.navigateToMeetingRollCall

@Composable
fun App() {
	val navController = rememberNavController()

	Surface { AppNavHost(navController) }
}

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

		associateDetails(onClickBackScreen = { navController.popBackStack() })

		associateForm(
			onClickBackScreen = { navController.popBackStack() },
			onClickConfirmButton = { navController.popBackStack() },
		)

		groupForm(
			onClickBackScreen = { navController.popBackStack() },
			onClickConfirmButton = { navController.popBackStack() },
		)

		projectForm(
			onClickBackScreen = { navController.popBackStack() },
			onClickConfirmButton = { navController.popBackStack() },
		)

		navigateToProjectDetails(
			onClickBackScreen = { navController.popBackStack() },
			onGroupClick = { }
		)

		dashboard(
			onClickMeetingBotton = { tabs -> navController.navigateToMeetingRollCall(tabs) },
			onClickListRegisters = { navController.navigateToContact() },
			onClickListEmployee = { navController.navigateToContact() },
		)

		meetingRollCall(onClickBack = { navController.popBackStack() })

		login(
			onNavigateToDashboard = { navController.navigateToDashboard() },
			onNavigateToRegister = { navController.navigateToRegister() },
		)

		register(
			onNavigateToLogin = { navController.navigateToLogin() },
			onRegisterSuccess = { navController.navigateToDashboard() }
		)
	}
}
