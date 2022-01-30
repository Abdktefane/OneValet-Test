package app.onevalet.devices.data.repositories

import app.onevalet.core.data.mappers.forLists
import app.onevalet.devices.data.datasources.DevicesDataSource
import app.onevalet.devices.data.mappers.DeviceModelToDeviceUiModel
import app.onevalet.devices.domain.repositories.DevicesRepository
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevicesRepositoryImpl @Inject constructor(
    private val devicesDataSource: DevicesDataSource,
    private val mapper: DeviceModelToDeviceUiModel
) : DevicesRepository() {
    private val _devices = MutableSharedFlow<List<DeviceUiModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override suspend fun getDevices(): List<DeviceUiModel> {
        return devicesDataSource.getDevices()
            .let { mapper.forLists().invoke(it) }
            .also {_devices.emit(it)}
    }


    override fun observeDevices(): Flow<List<DeviceUiModel>> {
        return _devices
    }
}