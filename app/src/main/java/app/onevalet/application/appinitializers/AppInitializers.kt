package app.onevalet.application.appinitializers

import android.app.Application
import app.onevalet.core.appinitializers.AppInitializer
import javax.inject.Inject


// Design Pattern to Prepare The App at Launch
class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) {
    fun init(application: Application) {
        initializers.forEach {
            it.init(application)
        }
    }
}
