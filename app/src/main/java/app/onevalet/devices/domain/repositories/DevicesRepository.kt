package app.onevalet.devices.domain.repositories

import app.onevalet.core.domain.repositories.OneValetRepository
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

abstract class DevicesRepository : OneValetRepository() {

    // base contract to fetch ui devices
    abstract suspend fun getDevices(): List<DeviceUiModel>

    // single source of truth for devices
    abstract fun observeDevices(): Flow<List<DeviceUiModel>>

}