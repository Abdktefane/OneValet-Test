package app.onevalet.devices.domain.interactors

import app.onevalet.core.data.models.InvokeError
import app.onevalet.core.data.models.InvokeStarted
import app.onevalet.core.data.models.InvokeSuccess
import app.onevalet.core.util.AppCoroutineDispatchers
import app.onevalet.devices.domain.repositories.DevicesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class UpdateDevicesInteractorTest {

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val appCoroutineDispatchers = AppCoroutineDispatchers(
        io = coroutineDispatcher,
        computation = coroutineDispatcher,
        main = coroutineDispatcher,
    )

    @Before
    fun setUp() {
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `it should emit InvokeStarted first then InvokeSuccess`() =
        coroutineDispatcher.runBlockingTest {
            val successMockedRepo: DevicesRepository = mockk()
            coEvery { successMockedRepo.getDevices() } returns listOf()
            val interactor = UpdateDevicesInteractor(
                devicesRepository = successMockedRepo,
                dispatchers = appCoroutineDispatchers,
            )

            val statuses = interactor(Unit).toList()
            assert(statuses.size == 2)
            assert(statuses.first() is InvokeStarted)
            assert(statuses.last() is InvokeSuccess)
        }


    @ExperimentalCoroutinesApi
    @Test
    fun `it should emit InvokeStarted first then InvokeError`() =
        coroutineDispatcher.runBlockingTest {
            val failedMockedRepo: DevicesRepository = mockk()
            coEvery { failedMockedRepo.getDevices() } throws IllegalAccessError()

            val interactor = UpdateDevicesInteractor(
                devicesRepository = failedMockedRepo,
                dispatchers = appCoroutineDispatchers,
            )

            val statuses = interactor(Unit).toList()
            assert(statuses.size == 2)
            assert(statuses.first() is InvokeStarted)
            assert(
                statuses.last() is InvokeError && (statuses.last() as InvokeError).throwable is IllegalAccessError
            )
        }
}