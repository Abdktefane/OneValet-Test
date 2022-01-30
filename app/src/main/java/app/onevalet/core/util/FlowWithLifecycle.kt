@file:Suppress("NOTHING_TO_INLINE")

package app.onevalet.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}


fun <T1, T2, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,

    transform: suspend (T1, T2) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(flow, flow2) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
    )
}