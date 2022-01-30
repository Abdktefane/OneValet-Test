package app.onevalet.devices.data.model

data class DeviceModel(
    val id: Int,
    val type: String,
    val price: Double,
    val currency: String,
    val isFavorite: Boolean,
    val imageUrl: String,
    val title: String,
    val description: String,
    )
