package app.onevalet.devices.domain.interactors

import app.onevalet.devices.domain.repositories.DevicesRepository
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import app.onevalet.devices.util.HelperTestData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


class ObserveDevicesTest {

    private val successMockedRepo: DevicesRepository = mockk()

    @ExperimentalCoroutinesApi
    @Test
    fun `it should emit the same value in repository devices observer`() = runBlockingTest {

        every { successMockedRepo.observeDevices() } returns flow {
            emit(HelperTestData.devicesUiModel)
        }

        val observer = ObserveDevices(devicesRepository = successMockedRepo)
        val testResults = mutableListOf<Pair<String?, List<DeviceUiModel>>>()

        // start observer in separate coroutine
        val job = launch {
            observer.flow.toList(testResults)
        }

        // trigger the observer without search term
        observer(ObserveDevices.Params())

        assert(testResults.size == 1)
        assert(testResults.first().first.isNullOrEmpty())
        assert(testResults.first().second.size == 2)
        assert(!testResults.first().second.first().isFavorite)
        assert(testResults.first().second.last().isFavorite)

        job.cancel()

    }


    @ExperimentalCoroutinesApi
    @Test
    fun `it should emit filtered repository devices with the search term`() = runBlockingTest {
        every { successMockedRepo.observeDevices() } returns flow {
            emit(HelperTestData.devicesUiModel)
        }
        val observer = ObserveDevices(devicesRepository = successMockedRepo)
        val testResults = mutableListOf<Pair<String?, List<DeviceUiModel>>>()

        // start observer in separate coroutine
        val job = launch {
            observer.flow.toList(testResults)
        }

        val searchTerm = "title1"

        // trigger the observer without search term
        observer(ObserveDevices.Params(query = searchTerm))

        assert(testResults.size == 1)
        assert(testResults.first().first == searchTerm)
        assert(testResults.first().second.size == 1)
        assert(testResults.first().second.first().title == "title1")

        job.cancel()

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `it should emit empty result with the search term`() = runBlockingTest {
        every { successMockedRepo.observeDevices() } returns flow {
            emit(HelperTestData.devicesUiModel)
        }


        val observer = ObserveDevices(devicesRepository = successMockedRepo)
        val testResults = mutableListOf<Pair<String?, List<DeviceUiModel>>>()

        // start observer in separate coroutine
        val job = launch {
            observer.flow.toList(testResults)
        }

        val searchTerm = "title5"

        // trigger the observer without search term
        observer(ObserveDevices.Params(query = searchTerm))

        assert(testResults.size == 1)
        assert(testResults.first().first == searchTerm)
        assert(testResults.first().second.isNullOrEmpty())

        job.cancel()
    }
}