package app.onevalet.devices.inject

import app.onevalet.devices.data.datasources.DevicesDataSource
import app.onevalet.devices.data.datasources.DevicesDataSourceImpl
import app.onevalet.devices.data.repositories.DevicesRepositoryImpl
import app.onevalet.devices.domain.repositories.DevicesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class DevicesModuleBind {
    @Singleton
    @Binds
    internal abstract fun provideDevicesRepository(bind: DevicesRepositoryImpl): DevicesRepository

    @Singleton
    @Binds
    internal abstract fun provideDevicesDataSource(bind: DevicesDataSourceImpl): DevicesDataSource

}
