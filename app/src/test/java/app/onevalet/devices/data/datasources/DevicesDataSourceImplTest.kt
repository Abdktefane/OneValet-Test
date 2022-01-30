package app.onevalet.devices.data.datasources

import app.onevalet.devices.data.services.DevicesService
import app.onevalet.devices.util.HelperTestData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.HttpException
import retrofit2.mock.Calls


class DevicesDataSourceImplTest {

    private val successRetrofitService: DevicesService = mockk()
    private val failedRetrofitService: DevicesService = mockk()

    @Test
    fun `it should fetch devices with success result`() {
        runBlocking {
            every { successRetrofitService.listDevices() } returns Calls.response(
                HelperTestData.devicesModel
            )

            val devices = DevicesDataSourceImpl(successRetrofitService).getDevices()
            assert(devices.size == 2)
            assert(!devices.first().isFavorite)
            assert(devices.last().isFavorite)
        }
    }

    @Test(expected = HttpException::class)
    fun `it should failed to fetch devices with http exception`() {
        runBlocking {
            every { failedRetrofitService.listDevices() } throws HelperTestData.throwHttpException()
            DevicesDataSourceImpl(failedRetrofitService).getDevices()
        }
    }
}


