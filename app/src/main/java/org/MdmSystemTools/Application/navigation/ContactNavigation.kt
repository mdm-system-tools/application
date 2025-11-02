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

@Serializable internal data object AssociateList

@Serializable internal data object AssociateForm

@Serializable internal data class AssociateProfileDetails(val id: Int)

@Serializable internal data object GroupList

@Serializable internal data object GroupForm

@Serializable internal data object ProjectList

@Serializable internal data object ProjectForm

fun NavGraphBuilder.associateList(
  onClickAssociateProfile: (associateId: Int) -> Unit,
  onClickFloatingButtom: () -> Unit,
  onClickBack: () -> Unit,
) {
  composable<AssociateList> {
    AssociateListScreen(
      onClickAssociateProfile = { id -> onClickAssociateProfile(id) },
      onClickFloatingButton = onClickFloatingButtom,
      onClickBack = onClickBack,
    )
  }
}

fun NavGraphBuilder.associateForm(onClickBackScreen: () -> Unit, onClickConfirmButton: () -> Unit) {
  composable<AssociateForm> {
    AssociateFormScreen(
      onClickIcon = onClickBackScreen,
      onClickConfirmButton = onClickConfirmButton,
    )
  }
}

fun NavGraphBuilder.associateProfileDetails(onClickBackScreen: () -> Unit) {
  composable<AssociateProfileDetails> { navBackStackEntry ->
    val id: Int = navBackStackEntry.toRoute<AssociateProfileDetails>().id
    Log.i("navegação", "chamada para associate profile details id $id")
    AssociateProfileDetails(id, onClickBackScreen, onClickEdit = {}, onCLickExport = {})
  }
}

fun NavController.navigateToAssociateForm() {
  navigate(route = AssociateForm)
}

fun NavController.navigateToAssociateList() {
  navigate(AssociateList)
}

fun NavController.navigateToAssociateProfileDetails(id: Int) {
  navigate(route = AssociateProfileDetails(id = id))
}
