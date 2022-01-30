@file:Suppress("DEPRECATION")

package app.onevalet.settings.inject

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import app.onevalet.core.settings.OneValetPreferences
import app.onevalet.settings.domain.OneValetPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class SettingsModuleBinds {
    @Singleton
    @Binds
    abstract fun providePreferences(bind: OneValetPreferencesImpl): OneValetPreferences
}

@InstallIn(SingletonComponent::class)
@Module
internal object SettingsModule {
    @Named("app")
    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}
