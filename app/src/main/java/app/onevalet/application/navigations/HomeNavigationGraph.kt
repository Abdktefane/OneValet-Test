package app.onevalet.application.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.onevalet.home.presentation.pages.HomePage
import com.google.accompanist.navigation.animation.navigation

@ExperimentalAnimationApi
fun NavGraphBuilder.addHomeNestedGraph(
    navController: NavController,
    toggleContainer: () -> Unit,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home),
    ) {
        addHomePage(navController, Screen.Home, toggleContainer)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHomePage(
    navController: NavController,
    root: Screen,
    toggleContainer: () -> Unit,
) {
    composable(
        route = LeafScreen.Home.createRoute(root),
    ) {
        HomePage(
            toggleContainer,
        ) {
            navController.navigate(LeafScreen.Devices.createRoute(Screen.Devices)) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }
}