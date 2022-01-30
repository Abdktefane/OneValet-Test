package app.onevalet.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.onevalet.core.settings.OneValetPreferences
import app.onevalet.core.util.Logger
import app.onevalet.core.util.ObservableLoadingCounter
import app.onevalet.core.util.UiMessageManager
import app.onevalet.core.util.collectStatus
import app.onevalet.devices.domain.interactors.ObserveDevices
import app.onevalet.devices.domain.interactors.UpdateDevicesInteractor
import app.onevalet.devices.presentation.viewmodel.DevicesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val pref: OneValetPreferences,
    private val logger: Logger,

    ) : ViewModel() {

    val state: StateFlow<SettingsState> = pref.observeTheme().map {
        logger.i("theme change to $it")
        SettingsState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsState.Empty,
    )

    fun changeTheme(theme: OneValetPreferences.Theme) {
        logger.i("theme changing to $theme")
        pref.theme = theme
    }

}