package org.MdmSystemTools.Application.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateProfileDetails
import org.MdmSystemTools.Application.view.screens.Registration.AssociateFormScreen

fun NavGraphBuilder.associate(
	onClickAssociateProfile: () -> Unit,
	onClickFloatingButtom: () -> Unit
) {
	composable<Route.Associate> {
		AssociateListScreen(
			onClickAssociateProfile = onClickAssociateProfile,
			onClickFloatingButtom = onClickFloatingButtom
		)
	}

}

fun NavGraphBuilder.associateForm(onClickConfirmButton: () -> Unit) {
	composable<Route.AssociateForm> {
		AssociateFormScreen(onClickConfirmButton)
	}
}

fun NavGraphBuilder.associateProfileDetails(onClickBackScreen: () -> Unit) {
	Log.i("navegação", "chamada para associate profile details")
	composable<Route.AssociateProfileDetails> {
		AssociateProfileDetails(onClickBackScreen)
	}
}

fun NavController.navigateToAssociateProfileDetails() {
	navigate(route = Route.AssociateProfileDetails)
}

fun NavController.navigateToAssociateForm() {
	navigate(route = Route.AssociateForm)
}
