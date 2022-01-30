package app.onevalet.devices.domain.interactors

import app.onevalet.core.domain.interactors.SubjectInteractor
import app.onevalet.devices.domain.repositories.DevicesRepository
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class ObserveDevices @Inject constructor(
    private val devicesRepository: DevicesRepository,
) : SubjectInteractor<ObserveDevices.Params, Pair<String?, List<DeviceUiModel>>>() {

    override fun createObservable(params: Params): Flow<Pair<String?, List<DeviceUiModel>>> =
        devicesRepository.observeDevices().map { devices ->
            if (params.query.isNullOrEmpty())
                return@map params.query to devices
            params.query to devices.filter { device ->
                device.title.contains(params.query, ignoreCase = true)
            }
        }


    data class Params(val query: String? = null)
}