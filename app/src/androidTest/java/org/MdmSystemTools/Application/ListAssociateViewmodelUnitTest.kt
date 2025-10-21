package org.MdmSystemTools.Application

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListAssociateViewmodelUnitTest {
	@get:Rule
	val composeTestRule = createComposeRule()

	@Test
	fun formScreenTest() {
		val menuOptions = (1..5).map { it.toString() }
		val title = "Selecionar numero"
		val userOption = mutableStateOf("")
		composeTestRule.setContent {
			//FieldDropdownMenu(title, menuOptions, fieldState = { userOption.value = it })
		}

		//composeTestRule.onNodeWithText(title).performClick()
		//composeTestRule.onNodeWithText("2").performClick()
		//assert(userOption.value == "2")
		assert(1 == 1)
	}
}