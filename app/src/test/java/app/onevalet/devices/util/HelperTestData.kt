package app.onevalet.devices.util

import app.onevalet.devices.data.model.DeviceModel
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

object HelperTestData {

    val devicesModel = listOf(
        DeviceModel(
            id = 123,
            title = "title1",
            imageUrl = "",
            currency = "USD",
            price = 20.0,
            type = "type",
            isFavorite = false,
            description = "desc"
        ),
        DeviceModel(
            id = 123,
            title = "title1",
            imageUrl = "",
            currency = "USD",
            price = 20.0,
            type = "type",
            isFavorite = true,
            description = "desc"
        )
    )

    val devicesUiModel = listOf(
        DeviceUiModel(
            id = 123,
            title = "title1",
            imageUrl = "",
            price = "USD 20.0",
            type = "type",
            isFavorite = false,
            Description = "desc"
        ),
        DeviceUiModel(
            id = 123,
            title = "title2",
            imageUrl = "",
            price = "USD 20.0",
            type = "type",
            isFavorite = true,
            Description = "desc"
        )
    )

    fun throwHttpException(code: Int = 404, message: String = "error"): HttpException {
        return HttpException(Response.error<String>(code, message.toResponseBody()))
    }
}