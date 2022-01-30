package app.onevalet.core.util

import app.onevalet.core.data.models.InvokeError
import app.onevalet.core.data.models.InvokeStarted
import app.onevalet.core.data.models.InvokeStatus
import app.onevalet.core.data.models.InvokeSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

suspend fun Flow<InvokeStatus>.collectStatus(
    counter: ObservableLoadingCounter,
    logger: Logger? = null,
    uiMessageManager: UiMessageManager? = null,
) = collect { status ->
    when (status) {
        InvokeStarted -> counter.addLoader()
        InvokeSuccess -> counter.removeLoader()
        is InvokeError -> {
            logger?.i(status.throwable)
            uiMessageManager?.emitMessage(UiMessage(status.throwable))
            counter.removeLoader()
        }
    }
}
