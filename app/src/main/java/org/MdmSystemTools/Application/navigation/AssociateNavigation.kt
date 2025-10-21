package org.MdmSystemTools.Application.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Registration.AssociateFormScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateListScreen
import org.MdmSystemTools.Application.view.screens.Registration.AssociateProfileDetails

@Serializable
internal data class AssociateProfileDetails(val id: Int)

fun NavGraphBuilder.associate(
	onClickAssociateProfile: (associateId : Int) -> Unit,
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

fun NavGraphBuilder.associateProfileDetails(onClickBackScreen: (id : Int) -> Unit) {
	Log.i("navegação", "chamada para associate profile details id $id")
	composable<AssociateProfileDetails> { navBackStackEntry ->
		AssociateProfileDetails(id = navBackStackEntry.toRoute(), onClickBackScreen as () -> Unit)
	}
}

fun NavController.navigateToAssociateProfileDetails(id: Int) {
	navigate(route = AssociateProfileDetails(id = id))
}

fun NavController.navigateToAssociateForm() {
	navigate(route = AssociateProfileDetails)
}
