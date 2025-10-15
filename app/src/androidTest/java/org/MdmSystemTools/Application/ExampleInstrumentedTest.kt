package org.MdmSystemTools.Application

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
	@get:Rule
	val composeTestRule = createComposeRule()

	@Test
	fun testaBotaoDoMateriali3() {
		composeTestRule.setContent {
			Button(onClick = {}) {
				Text("teste")
			}
		}

		composeTestRule.onNodeWithText("teste").assertIsDisplayed()
	}

	sealed class NavRoutes(val label: String) {
		class Assoc : NavRoutes("assoc")
		class Colla : NavRoutes("colla")
		class Calen : NavRoutes("calen")


	}

	@Test
	fun testNavigationWithSerializableRoute() {
		composeTestRule.setContent {
			val navController = rememberNavController()
			val navBackStackEntry by navController.currentBackStackEntryAsState()
			val currentDestination = navBackStackEntry?.destination

			val ROUTES = listOf<Route>(
				TestAssoc("assoc"),
				TestColla("colla"),
				TestCalen("calen")
			)

			NavigationSuiteScaffold(
				navigationSuiteItems = {
					ROUTES.forEach {
						item(
							icon = { Icon(Icons.Default.Route, contentDescription = "") },
							label = { Text(it.label) },
							selected = currentDestination?.hasRoute(it::class) ?: false,
							onClick = { navController.navigate(it) }
						)
					}
				}
			) {
				NavHost(navController, startDestination = TestAssoc) {
					composable<TestAssoc> { Text("Display assoc") }
					composable<TestColla> { Text("Display colla") }
					composable<TestCalen> { Text("Display calen") }
				}
			}
		}

		composeTestRule.onNodeWithText("colla").performClick()
		composeTestRule.onNodeWithText("Display colla").assertIsDisplayed()
	}

	///@Test
	///fun testBottomBarNavigation() {
	///	composeTestRule.setContent {
	///		val navController = rememberNavController()
	///		val navBackStackEntry by navController.currentBackStackEntryAsState()
	///		val currentDestination = navBackStackEntry?.destination

	///		val items = listOf<Route>(TestAssoc, TestCalen, TestColla)

	///		NavigationSuiteScaffold(
	///			navigationSuiteItems = {
	///				items.forEach { route ->
	///					item(
	///						icon = { Icon(Icons.Default.Route, contentDescription = null) },
	///						label = { Text(route.label) },
	///						selected = currentDestination?.hasRoute(route::class) ?: false,
	///						onClick = { navController.navigate(route) }
	///					)
	///				}
	///			}
	///		) {
	///			NavHost(navController, startDestination = "assoc") {
	///				composable<TestRoute.TestAssoc> { Text("Display assoc") }
	///				composable<TestRoute.TestColla> { Text("Display colla") }
	///				composable<TestRoute.TestCalen> { Text("Display calen") }
	///			}
	///		}
	///	}

	///	composeTestRule.onNodeWithText("colla").performClick()
	///	composeTestRule.onNodeWithText("Display colla").assertIsDisplayed()
	///}
}