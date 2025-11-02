package org.MdmSystemTools.Application.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.MdmSystemTools.Application.view.screens.Collaborators.CollaboratorsScreen

fun NavGraphBuilder.collaboration() {
  composable<BottomNav.Contact> { CollaboratorsScreen() }
}
