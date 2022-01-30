package app.onevalet.application.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.onevalet.devicedetails.presentation.pages.DevicesDetailsPage
import app.onevalet.devices.presentation.pages.DevicesPage
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import com.google.accompanist.navigation.animation.navigation


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addDevicesNestedGraph(
    navController: NavController,
    toggleContainer: () -> Unit,
) {
    navigation(
        route = Screen.Devices.route,
        startDestination = LeafScreen.Devices.createRoute(Screen.Devices),
    ) {
        addDevicesPage(toggleContainer, navController, Screen.Devices)
        addDeviceDetailsPage(navController, Screen.Devices)
        // TODO(abd): add search page
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addDevicesPage(
    toggleContainer: () -> Unit,
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.Devices.createRoute(root),
    ) {
        DevicesPage(
            toggleContainer = toggleContainer,
            openDeviceDetails = { device ->
                navController.navigate(
                    LeafScreen.DeviceDetails.createRoute(root, device)
                )
            }
        )
    }
}


@ExperimentalAnimationApi
private fun NavGraphBuilder.addDeviceDetailsPage(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.DeviceDetails.createRoute(root),
        arguments = listOf(
            navArgument("device") { type = NavType.StringType }
        ),
    ) { backStackEntry ->
        DevicesDetailsPage(
            navigateUp = navController::navigateUp,
            device = DeviceUiModel.fromJson(backStackEntry.arguments?.getString("device")!!)
        )
    }
}