package app.onevalet.devices.data.repositories

import app.onevalet.devices.data.datasources.DevicesDataSource
import app.onevalet.devices.data.mappers.DeviceModelToDeviceUiModel
import app.onevalet.devices.util.HelperTestData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.HttpException

class DevicesRepositoryImplTest {

    private val successDevicesDataSource: DevicesDataSource = mockk()
    private val failedDevicesDataSource: DevicesDataSource = mockk()

    @Test
    fun `it should fetch devices with success result`() = runBlocking {
        coEvery { successDevicesDataSource.getDevices() } returns HelperTestData.devicesModel
        val repository = DevicesRepositoryImpl(
            mapper = DeviceModelToDeviceUiModel(),
            devicesDataSource = successDevicesDataSource,
        )

        val devices = repository.getDevices()
        assert(devices.size == 2)
        assert(!devices.first().isFavorite)
        assert(devices.last().isFavorite)

        // assert the new devices assigned to observer
        val devicesFromFlow = repository.observeDevices().first()
        assert(devices == devicesFromFlow)

    }

    @Test(expected = HttpException::class)
    fun `it should failed to fetch ui devices with http exception`() {
        runBlocking {
            coEvery { failedDevicesDataSource.getDevices() } throws HelperTestData.throwHttpException()
            DevicesRepositoryImpl(
                mapper = DeviceModelToDeviceUiModel(),
                devicesDataSource = failedDevicesDataSource,
            ).getDevices()
        }
    }
}