package app.onevalet.application.appinitializers

import android.app.Application
import app.onevalet.core.appinitializers.AppInitializer
import app.onevalet.core.settings.OneValetPreferences
import javax.inject.Inject

class PreferencesInitializer @Inject constructor(
    private val prefs: OneValetPreferences
) : AppInitializer {
    override fun init(application: Application) {
        prefs.setup()
    }
}
