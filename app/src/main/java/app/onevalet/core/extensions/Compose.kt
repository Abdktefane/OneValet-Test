package app.onevalet.core.extensions

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import app.onevalet.core.settings.OneValetPreferences
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

@Composable
fun OneValetPreferences.shouldUseDarkColors(): Boolean {
    val themePreference = observeTheme().collectAsState(initial = OneValetPreferences.Theme.SYSTEM)
    return when (themePreference.value) {
        OneValetPreferences.Theme.LIGHT -> false
        OneValetPreferences.Theme.DARK -> true
        else -> isSystemInDarkTheme()
    }
}