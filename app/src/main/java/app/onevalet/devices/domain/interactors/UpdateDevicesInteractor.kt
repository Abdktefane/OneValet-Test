package app.onevalet.devices.domain.interactors

import app.onevalet.core.domain.interactors.Interactor
import app.onevalet.core.util.AppCoroutineDispatchers
import app.onevalet.devices.domain.repositories.DevicesRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UpdateDevicesInteractor @Inject constructor(
    private val devicesRepository: DevicesRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : Interactor<Unit>() {
    override suspend fun doWork(params: Unit) {
        withContext(dispatchers.io) {
            devicesRepository.getDevices()
        }
    }
}
