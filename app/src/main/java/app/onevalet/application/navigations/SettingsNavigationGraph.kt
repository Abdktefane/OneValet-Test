package app.onevalet.application.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.onevalet.settings.presentation.pages.SettingsPage
import com.google.accompanist.navigation.animation.navigation

@ExperimentalAnimationApi
fun NavGraphBuilder.addSettingsNestedGraph(
    navController: NavController,
    toggleContainer: () -> Unit,
) {
    navigation(
        route = Screen.Settings.route,
        startDestination = LeafScreen.Settings.createRoute(Screen.Settings),
    ) {
        addSettingsPage(navController, Screen.Settings, toggleContainer)
    }
}


@ExperimentalAnimationApi
private fun NavGraphBuilder.addSettingsPage(
    navController: NavController,
    root: Screen,
    toggleContainer: () -> Unit,
) {
    composable(
        route = LeafScreen.Settings.createRoute(root),
    ) {
        SettingsPage(toggleContainer)
    }
}