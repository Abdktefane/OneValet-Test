package app.onevalet.core.ui.composables

import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Wrapper around [Snackbar] to make it swipe-dismissable,
 * using [SwipeToDismiss].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeDismissSnackbar(
    data: SnackbarData,
    onDismiss: (() -> Unit)? = null,
    snackbar: @Composable (SnackbarData) -> Unit = { Snackbar(it) },
) {
    val dismissState = rememberDismissState {
        if (it != DismissValue.Default) {
            // First dismiss the snackbar
            data.dismiss()
            // Then invoke the callback
            onDismiss?.invoke()
        }
        true
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        background = {},
        dismissContent = { snackbar(data) }
    )
}

@Composable
fun SwipeDismissSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = { hostState.currentSnackbarData?.dismiss() },
    snackbar: @Composable (SnackbarData) -> Unit = { data ->
        SwipeDismissSnackbar(
            data = data,
            onDismiss = onDismiss,
        )
    },
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = snackbar,
        modifier = modifier,
    )
}
