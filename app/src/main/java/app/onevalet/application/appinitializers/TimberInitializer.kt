package app.onevalet.application.appinitializers

import android.app.Application
import app.onevalet.BuildConfig
import app.onevalet.core.appinitializers.AppInitializer
import app.onevalet.core.util.Logger
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val logger: Logger
) : AppInitializer {
    override fun init(application: Application) = logger.setup(BuildConfig.DEBUG)
}
