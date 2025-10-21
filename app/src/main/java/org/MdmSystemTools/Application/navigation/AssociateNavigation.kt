package org.MdmSystemTools.Application.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Registration.AssociateFormScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateProfileDetails

@Serializable
internal data class AssociateProfileDetails(val id: Int)

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

fun NavGraphBuilder.associateForm(
	onClickBackScreen: () -> Unit,
	onClickConfirmButton: () -> Unit
) {
	composable<Route.AssociateForm> {
		AssociateFormScreen(
			onClickIcon = onClickBackScreen,
			onClickConfirmButton = onClickConfirmButton
		)
	}
}

fun NavGraphBuilder.associateProfileDetails(onClickBackScreen: () -> Unit) {
	Log.i("navegação", "chamada para associate profile details id $id")
	composable<AssociateProfileDetails> {
		AssociateProfileDetails(onClickBackScreen)
	}
}

fun NavController.navigateToAssociateProfileDetails(id: Int) {
	navigate(route = AssociateProfileDetails(id = id))
}

fun NavController.navigateToAssociateForm() {
	navigate(route = Route.AssociateForm)
}
