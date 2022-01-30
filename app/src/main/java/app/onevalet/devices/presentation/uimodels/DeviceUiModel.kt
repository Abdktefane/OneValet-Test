package app.onevalet.devices.presentation.uimodels

import com.google.gson.Gson

data class DeviceUiModel(
    val id: Int,
    val type: String,
    val price: String,
    val isFavorite: Boolean,
    val imageUrl: String,
    val title: String,
    val Description: String,
) {
    fun toJson() = Gson().toJson(this)

    companion object {
        fun fromJson(json: String): DeviceUiModel = Gson().fromJson(json, DeviceUiModel::class.java)
    }
}
