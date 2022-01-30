package app.onevalet.settings.presentation.viewmodel

import app.onevalet.core.settings.OneValetPreferences

data class SettingsState(
    val selectedTheme: OneValetPreferences.Theme = OneValetPreferences.Theme.SYSTEM,
) {
    companion object {
        val Empty = SettingsState()
    }
}
