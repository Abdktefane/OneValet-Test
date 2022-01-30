package app.onevalet.devices.presentation.viewmodel

import app.onevalet.core.data.models.InvokeStarted
import app.onevalet.core.data.models.InvokeSuccess
import app.onevalet.core.util.Logger
import app.onevalet.devices.domain.interactors.ObserveDevices
import app.onevalet.devices.domain.interactors.UpdateDevicesInteractor
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import app.onevalet.devices.util.HelperTestData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DevicesViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()
    private val logger: Logger = mockk()
    private val updateDevicesInteractor: UpdateDevicesInteractor = mockk()

    private val observeDevices: ObserveDevices = mockk()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check view state with devices when search term is empty`() = runBlockingTest {

        val mutableSharedFlow = MutableSharedFlow<Pair<String?, List<DeviceUiModel>>>()

        every { updateDevicesInteractor(Unit) } returns flow {
            emit(InvokeStarted)
            emit(InvokeSuccess)
        }

        every { observeDevices(ObserveDevices.Params()) } returns Unit
        every { observeDevices.flow } returns mutableSharedFlow


        val devicesViewModel = DevicesViewModel(
            logger = logger,
            observeDevices = observeDevices,
            updateDevices = updateDevicesInteractor,
        )

        val testResults = mutableListOf<DevicesState>()

        // start observer in separate coroutine
        val job = launch {
            devicesViewModel.state.toList(testResults)
        }

        print(testResults)

        mutableSharedFlow.emit("" to HelperTestData.devicesUiModel)

        assert(
            testResults.last() == DevicesState(
                refreshing = false,
                searchQuery = "",
                message = null,
                devices = HelperTestData.devicesUiModel
            )
        )


        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check view state with devices when search term is not Empty`() = runBlockingTest {

        val mutableSharedFlow = MutableSharedFlow<Pair<String?, List<DeviceUiModel>>>()

        every { updateDevicesInteractor(Unit) } returns flow {
            emit(InvokeStarted)
            emit(InvokeSuccess)
        }

        every { observeDevices(ObserveDevices.Params()) } returns Unit
        every { observeDevices.flow } returns mutableSharedFlow


        val devicesViewModel = DevicesViewModel(
            logger = logger,
            observeDevices = observeDevices,
            updateDevices = updateDevicesInteractor,
        )

        val testResults = mutableListOf<DevicesState>()

        // start observer in separate coroutine
        val job = launch {
            devicesViewModel.state.toList(testResults)
        }

        print(testResults)

        mutableSharedFlow.emit("title1" to listOf(HelperTestData.devicesUiModel.first()))

        assert(
            testResults.last() == DevicesState(
                refreshing = false,
                searchQuery = "title1",
                message = null,
                devices = listOf(HelperTestData.devicesUiModel.first())
            )
        )


        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check view state with empty devices when search term is not Empty`() = runBlockingTest {

        val mutableSharedFlow = MutableSharedFlow<Pair<String?, List<DeviceUiModel>>>()

        every { updateDevicesInteractor(Unit) } returns flow {
            emit(InvokeStarted)
            emit(InvokeSuccess)
        }

        every { observeDevices(ObserveDevices.Params()) } returns Unit
        every { observeDevices.flow } returns mutableSharedFlow


        val devicesViewModel = DevicesViewModel(
            logger = logger,
            observeDevices = observeDevices,
            updateDevices = updateDevicesInteractor,
        )

        val testResults = mutableListOf<DevicesState>()

        // start observer in separate coroutine
        val job = launch {
            devicesViewModel.state.toList(testResults)
        }

        print(testResults)

        mutableSharedFlow.emit("title5" to listOf())

        assert(
            testResults.last() == DevicesState(
                refreshing = false,
                searchQuery = "title5",
                message = null,
                devices = listOf()
            )
        )


        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}