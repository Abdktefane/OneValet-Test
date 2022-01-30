package app.onevalet.devices.inject

import app.onevalet.devices.data.services.DevicesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DevicesModule {
    @Singleton
    @Provides
    fun provideRetrofitService(
        client: Retrofit,
    ): DevicesService = client.create(DevicesService::class.java)

}