package org.MdmSystemTools.Application.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Contact.ContactScreen
import org.MdmSystemTools.Application.view.screens.Contact.Tabs
import org.MdmSystemTools.Application.view.screens.Contact.associate.AssociateFormScreen
import org.MdmSystemTools.Application.view.screens.Contact.associate.AssociateProfileDetails
import org.MdmSystemTools.Application.view.screens.Contact.group.GroupFormScreen
import org.MdmSystemTools.Application.view.screens.Contact.projetc.CriarNovoProjetoScreen
import org.MdmSystemTools.Application.view.screens.Contact.projetc.ProjetoDetalhesScreen

@Serializable internal data object AssociateList

@Serializable internal data object AssociateForm

@Serializable internal data class AssociateProfileDetails(val id: Int)

@Serializable internal data object GroupList

@Serializable internal data object GroupForm

@Serializable internal data class GroupProfileDetails(val id: Int)

@Serializable internal data object ProjectList

@Serializable internal data object ProjectForm

@Serializable internal data object CriarNovoProjeto

@Serializable internal data class ProjectProfileDetails(val id: Int)

fun NavGraphBuilder.contact(
  onBack: () -> Unit,
  onClickAdd: (Tabs) -> Unit,
  onClickItem: (Int, Tabs) -> Unit,
) {
  composable<BottomNav.Contact> { ContactScreen(onBack, onClickAdd, onClickItem) }
}

fun NavController.navigateToContact() {
  navigate(BottomNav.Contact)
}

fun NavGraphBuilder.associateForm(onClickBackScreen: () -> Unit, onClickConfirmButton: () -> Unit) {
  composable<AssociateForm> {
    AssociateFormScreen(
      onClickIcon = onClickBackScreen,
      onClickConfirmButton = onClickConfirmButton,
    )
  }
}

fun NavGraphBuilder.groupForm(onClickBackScreen: () -> Unit, onClickConfirmButton: () -> Unit) {
  composable<GroupForm> { GroupFormScreen(onClickBackScreen, onClickConfirmButton) }
}

fun NavGraphBuilder.criarNovoProjeto(onClickBackScreen: () -> Unit, onClickConfirmButton: () -> Unit) {
  composable<CriarNovoProjeto> {
    CriarNovoProjetoScreen(
      onBackClick = onClickBackScreen,
      onCancelarClick = onClickBackScreen,
      onCriarProjetoClick = onClickConfirmButton
    )
  }
}

fun NavGraphBuilder.projetoDetalhes(onClickBackScreen: () -> Unit, onGrupoClick: () -> Unit) {
  composable<ProjectProfileDetails> { navBackStackEntry ->
    val id: Int = navBackStackEntry.toRoute<ProjectProfileDetails>().id
    ProjetoDetalhesScreen(
      projetoId = id,
      onBackClick = onClickBackScreen,
      onGrupoClick = { onGrupoClick() }
    )
  }
}

fun NavController.navigateToGroupForm() {
  navigate(GroupForm)
}

fun NavController.navigateToCriarNovoProjeto() {
  navigate(CriarNovoProjeto)
}

fun NavController.navigateToProjetoDetalhes(id: Int) {
  navigate(ProjectProfileDetails(id))
}

fun NavGraphBuilder.associateProfileDetails(onClickBackScreen: () -> Unit) {
  composable<AssociateProfileDetails> { navBackStackEntry ->
    val id: Int = navBackStackEntry.toRoute<AssociateProfileDetails>().id
    Log.i("navegação", "chamada para associate profile details id $id")
    AssociateProfileDetails(id, onClickBackScreen, onClickEdit = {}, onCLickExport = {})
  }
}

fun NavController.navigateToDetailsByTab(tab: Tabs) {
  when (tab) {
    Tabs.ASSOCIATE -> navigate(AssociateForm)
    Tabs.GROUP -> navigate(GroupForm)
    Tabs.PROJECT -> navigate(CriarNovoProjeto)
  }
}

fun NavController.navigateToAssociateList() {
  navigate(AssociateList)
}

fun NavController.navigateToDetailsByTab(id: Int, tab: Tabs) {
  when (tab) {
    Tabs.ASSOCIATE -> navigate(AssociateProfileDetails(id))
    Tabs.GROUP -> navigate(GroupProfileDetails(id))
    Tabs.PROJECT -> navigate(ProjectProfileDetails(id))
  }
}
