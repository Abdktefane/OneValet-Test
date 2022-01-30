package app.onevalet.devices.presentation.pages

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.onevalet.core.extensions.rememberFlowWithLifecycle
import app.onevalet.core.ui.composables.SwipeDismissSnackbarHost
import app.onevalet.devices.presentation.composables.DeviceRow
import app.onevalet.devices.presentation.composables.DevicesTopBar
import app.onevalet.devices.presentation.viewmodel.DevicesState
import app.onevalet.devices.presentation.viewmodel.DevicesViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalComposeUiApi
@Composable
fun DevicesPage(
    openDeviceDetails: (device: String) -> Unit,
    toggleContainer: () -> Unit,
) {
    DevicesPage(
        viewModel = hiltViewModel(),
        openDeviceDetails = openDeviceDetails,
        toggleContainer = toggleContainer,
    )
}


@ExperimentalComposeUiApi
@Composable
private fun DevicesPage(
    viewModel: DevicesViewModel,
    openDeviceDetails: (device: String) -> Unit,
    toggleContainer: () -> Unit,

    ) {
    val viewState by rememberFlowWithLifecycle(viewModel.state)
        .collectAsState(initial = DevicesState.Empty)

    DevicesPage(
        state = viewState,
        onMessageShown = { viewModel.clearMessage(it) },
        refresh = { viewModel.refresh() },
        openDeviceDetails = openDeviceDetails,
        toggleContainer = toggleContainer,
        onSearchTextChanged = viewModel::searchOnDevice
    )
}


@ExperimentalComposeUiApi
@Composable
private fun DevicesPage(
    state: DevicesState,
    onMessageShown: (id: Long) -> Unit,
    refresh: () -> Unit,
    openDeviceDetails: (device: String) -> Unit,
    toggleContainer: () -> Unit,
    onSearchTextChanged: (String) -> Unit,

    ) {
    val scaffoldState = rememberScaffoldState()

    state.message?.let { message ->
        LaunchedEffect(message) {
            scaffoldState.snackbarHostState.showSnackbar(message.message)
            onMessageShown(message.id)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DevicesTopBar(
                searchQuery = state.searchQuery,
                toggleContainer = {
                    toggleContainer()
                    onSearchTextChanged("")
                },
                onSearchTextChanged = onSearchTextChanged
            )
        },
        snackbarHost = { snackbarHostState ->
            SwipeDismissSnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        SwipeRefresh(
            state = rememberSwipeRefreshState(state.refreshing),
            onRefresh = refresh,
            indicatorPadding = paddingValues,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true
                )
            }
        ) {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 8.0.dp, vertical = 4.0.dp),
            ) {
                items(state.devices) { device ->
                    DeviceRow(openDeviceDetails = {
                        openDeviceDetails(device.toJson())
                        onSearchTextChanged("")
                    }, device = device)
                }
            }
        }

    }
}


