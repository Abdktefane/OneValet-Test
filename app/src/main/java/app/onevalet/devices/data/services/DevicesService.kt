package app.onevalet.devices.data.services

import app.onevalet.devices.data.model.DeviceModel
import retrofit2.Call
import retrofit2.http.GET


interface DevicesService {

    @GET("devices/list")
    fun listDevices(): Call<List<DeviceModel>>
}