package app.onevalet.application.presentation.page

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import app.onevalet.application.navigations.AppNavigation
import app.onevalet.application.navigations.Screen
import app.onevalet.core.ui.composables.OneValetDrawer
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
internal fun App() {
    val navController = rememberAnimatedNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),

        drawerElevation = 5.dp,
        drawerContent = {
            val currentSelectedItem by navController.currentScreenAsState()
            OneValetDrawer(
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    navController.navigate(selected.route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }

                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        AppNavigation(
            navController = navController,
            modifier = Modifier.fillMaxHeight(),
            toggleContainer = {
                scope.launch {
                    scaffoldState.drawerState.apply {
                        if (isOpen) close() else open()
                    }
                }
            }
        )
    }
}


/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Screen.Home.route } -> {
                    selectedItem.value = Screen.Home
                }
                destination.hierarchy.any { it.route == Screen.Devices.route } -> {
                    selectedItem.value = Screen.Devices
                }
                destination.hierarchy.any { it.route == Screen.Settings.route } -> {
                    selectedItem.value = Screen.Settings
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}


