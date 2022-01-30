package app.onevalet.devices.data.mappers

import app.onevalet.core.data.mappers.Mapper
import app.onevalet.devices.data.model.DeviceModel
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceModelToDeviceUiModel @Inject constructor() : Mapper<DeviceModel, DeviceUiModel> {
    override suspend fun map(from: DeviceModel): DeviceUiModel {
        return DeviceUiModel(
            id = from.id,
            imageUrl = from.imageUrl,
            title = from.title,
            Description = from.description,
            price = "${from.currency} ${from.price}",
            type = from.type,
            isFavorite = from.isFavorite,
        )
    }

}
