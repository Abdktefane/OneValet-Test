package app.onevalet.devices.presentation.viewmodel

import androidx.compose.runtime.Immutable
import app.onevalet.core.util.UiMessage
import app.onevalet.devices.data.model.DeviceModel
import app.onevalet.devices.presentation.uimodels.DeviceUiModel


@Immutable
data class DevicesState(
    val devices: List<DeviceUiModel> = emptyList(),
    val refreshing: Boolean = false,
    val searchQuery: String = "",
    val message: UiMessage? = null,
) {


    companion object {
        val Empty = DevicesState()
    }
}