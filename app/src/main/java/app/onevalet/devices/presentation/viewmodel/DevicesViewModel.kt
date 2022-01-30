package app.onevalet.devices.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.onevalet.core.util.Logger
import app.onevalet.core.util.ObservableLoadingCounter
import app.onevalet.core.util.UiMessageManager
import app.onevalet.core.util.collectStatus
import app.onevalet.devices.domain.interactors.ObserveDevices
import app.onevalet.devices.domain.interactors.UpdateDevicesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val updateDevices: UpdateDevicesInteractor,
    private val observeDevices: ObserveDevices,
    private val logger: Logger,

    ) : ViewModel() {

    private val devicesLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()


    val state: StateFlow<DevicesState> = combine(
        observeDevices.flow,
        devicesLoadingState.observable,

        ) {
            devices,
            devicesLoad,
        ->
        DevicesState(
            searchQuery = devices.first ?: "",
            devices = devices.second,
            refreshing = devicesLoad
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DevicesState.Empty,
    )


    init {
        observeDevices(ObserveDevices.Params())
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            updateDevices(Unit).collectStatus(devicesLoadingState, logger, uiMessageManager)
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }

    fun searchOnDevice(query: String?) {
        observeDevices(ObserveDevices.Params(query))
    }

}

