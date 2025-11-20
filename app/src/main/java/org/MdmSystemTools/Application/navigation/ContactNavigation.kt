package org.MdmSystemTools.Application.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.MdmSystemTools.Application.view.screens.Contact.ContactScreen
import org.MdmSystemTools.Application.view.screens.Contact.TabsForContact
import org.MdmSystemTools.Application.view.screens.Contact.associate.AssociateFormScreen
import org.MdmSystemTools.Application.view.screens.Contact.associate.AssociateProfileDetails
import org.MdmSystemTools.Application.view.screens.Contact.group.GroupFormScreen
import org.MdmSystemTools.Application.view.screens.Contact.project.ProjectDetailsScreen
import org.MdmSystemTools.Application.view.screens.Contact.project.ProjectFormScreen

@Serializable
internal data object Contact

@Serializable
internal data object AssociateForm

@Serializable
internal data class AssociateDetails(val id: Int)

@Serializable
internal data object GroupForm

@Serializable
internal data class GroupDetails(val id: Int)

@Serializable
internal data object ProjectForm

@Serializable
internal data class ProjectDetails(val id: Int)

fun NavGraphBuilder.contact(
	onBack: () -> Unit,
	onClickAdd: (TabsForContact) -> Unit,
	onClickItem: (Int, TabsForContact) -> Unit,
) {
	composable<Contact> { ContactScreen(onBack, onClickAdd, onClickItem) }
}

fun NavController.navigateToContact() {
	navigate(Contact)
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

fun NavGraphBuilder.projectForm(
	onClickBackScreen: () -> Unit,
	onClickConfirmButton: () -> Unit
) {
	composable<ProjectForm> {
		ProjectFormScreen(
			onBackClick = onClickBackScreen,
			onCancelarClick = onClickBackScreen,
			onCriarProjetoClick = onClickConfirmButton
		)
	}
}

fun NavGraphBuilder.navigateToProjectDetails(
	onClickBackScreen: () -> Unit,
	onGroupClick: () -> Unit
) {
	composable<ProjectDetails> { navBackStackEntry ->
		val id: Int = navBackStackEntry.toRoute<ProjectDetails>().id
		ProjectDetailsScreen(
			projetoId = id,
			onBackClick = onClickBackScreen,
			onGrupoClick = { onGroupClick() }
		)
	}
}

fun NavController.navigateToProjectDetails(id: Int) {
	navigate(ProjectDetails(id))
}

fun NavGraphBuilder.associateDetails(onClickBackScreen: () -> Unit) {
	composable<AssociateDetails> { navBackStackEntry ->
		val id: Int = navBackStackEntry.toRoute<AssociateDetails>().id
		navBackStackEntry.savedStateHandle["id"] = id
		Log.i("navegação", "chamada para associate profile details id $id")
		AssociateProfileDetails(id, onClickBackScreen, onClickEdit = {}, onCLickExport = {})
	}
}

fun NavController.navigateToDetailsByTab(tab: TabsForContact) {
	when (tab) {
		TabsForContact.ASSOCIATE -> navigate(AssociateForm)
		TabsForContact.GROUP -> navigate(GroupForm)
		TabsForContact.PROJECT -> navigate(ProjectForm)
	}
}

fun NavController.navigateToDetailsByTab(id: Int, tab: TabsForContact) {
	when (tab) {
		TabsForContact.ASSOCIATE -> navigate(AssociateDetails(id))
		TabsForContact.GROUP -> navigate(GroupDetails(id))
		TabsForContact.PROJECT -> navigate(ProjectDetails(id))
	}
}
