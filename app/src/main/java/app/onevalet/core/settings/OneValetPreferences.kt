package app.onevalet.core.settings

import kotlinx.coroutines.flow.Flow

interface OneValetPreferences {

    fun setup()

    var theme: Theme
    fun observeTheme(): Flow<Theme>


    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }
}
