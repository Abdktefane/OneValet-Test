package app.onevalet.devices.data.datasources

import app.onevalet.core.data.datasources.OneValetDataSource
import app.onevalet.core.data.mappers.forLists
import app.onevalet.core.extensions.bodyOrThrow
import app.onevalet.core.extensions.withRetry
import app.onevalet.devices.data.mappers.DeviceModelToDeviceUiModel
import app.onevalet.devices.data.model.DeviceModel
import app.onevalet.devices.data.services.DevicesService
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import retrofit2.awaitResponse
import javax.inject.Inject

// base contract for devices data source
abstract class DevicesDataSource : OneValetDataSource() {
    abstract suspend fun getDevices(): List<DeviceModel>
}


class DevicesDataSourceImpl @Inject constructor(
    private val devicesService: DevicesService,
) : DevicesDataSource() {

    override suspend fun getDevices(): List<DeviceModel> {
        return withRetry {
            devicesService
                .listDevices()
                .awaitResponse()
                .bodyOrThrow()
        }
    }
}
