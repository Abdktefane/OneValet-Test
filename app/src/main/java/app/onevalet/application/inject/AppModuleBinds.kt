
package app.onevalet.application.inject

import app.onevalet.application.appinitializers.PreferencesInitializer
import app.onevalet.application.appinitializers.TimberInitializer
import app.onevalet.core.appinitializers.AppInitializer
import app.onevalet.core.util.Logger
import app.onevalet.core.util.OneValetLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {
    @Binds
    @IntoSet
    abstract fun providePreferencesInitializer(bind: PreferencesInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(bind: TimberInitializer): AppInitializer

    @Singleton
    @Binds
    internal abstract fun provideLogger(bind: OneValetLogger): Logger

}
