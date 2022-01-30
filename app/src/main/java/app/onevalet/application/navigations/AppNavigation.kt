package app.onevalet.application.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.onevalet.devicedetails.presentation.pages.DevicesDetailsPage
import app.onevalet.devices.presentation.pages.DevicesPage
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import app.onevalet.home.presentation.pages.HomePage
import app.onevalet.settings.presentation.pages.SettingsPage
import com.google.accompanist.navigation.animation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Devices : Screen("devices")
    object Settings : Screen("search")
}

sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Home : LeafScreen("home")
    object Devices : LeafScreen("devices")
    object Search : LeafScreen("search")
    object Settings : LeafScreen("home")

    object DeviceDetails : LeafScreen("device/{device}") {
        fun createRoute(root: Screen, device: String): String {
            return "${root.route}/device/$device"
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    toggleContainer: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Devices.route,
        modifier = modifier,
    ) {

        addHomeNestedGraph(navController, toggleContainer)
        addDevicesNestedGraph(navController, toggleContainer)
        addSettingsNestedGraph(navController, toggleContainer)
    }
}






