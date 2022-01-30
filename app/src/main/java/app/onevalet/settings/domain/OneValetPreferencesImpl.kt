package app.onevalet.settings.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import app.onevalet.R
import app.onevalet.core.settings.OneValetPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Named

class OneValetPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("app") private val sharedPreferences: SharedPreferences
) : OneValetPreferences {
    private val defaultThemeValue = "system"

    private val preferenceKeyChangedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        preferenceKeyChangedFlow.tryEmit(key)
    }

    companion object {
        const val KEY_THEME = "pref_theme"
    }

    override fun setup() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override var theme: OneValetPreferences.Theme
        get() = getThemeForStorageValue(sharedPreferences.getString(KEY_THEME, defaultThemeValue)!!)
        set(value) =
            sharedPreferences.edit {
                putString(KEY_THEME, value.storageKey)
            }


    override fun observeTheme(): Flow<OneValetPreferences.Theme> {
        return preferenceKeyChangedFlow
            // Emit on start so that we always send the initial value
            .onStart { emit(KEY_THEME) }
            .filter { it == KEY_THEME }
            .map { theme }
            .distinctUntilChanged()
    }

    private val OneValetPreferences.Theme.storageKey: String
        get() = when (this) {
            OneValetPreferences.Theme.LIGHT -> "light"
            OneValetPreferences.Theme.DARK -> "dark"
            OneValetPreferences.Theme.SYSTEM -> "system"
        }

    //
    private fun getThemeForStorageValue(value: String) = when (value) {
        "light" -> OneValetPreferences.Theme.LIGHT
        "dark" -> OneValetPreferences.Theme.DARK
        else -> OneValetPreferences.Theme.SYSTEM
    }
}
