package app.onevalet.devicedetails.presentation.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.onevalet.R
import app.onevalet.core.ui.composables.OneValetTopBar
import app.onevalet.devices.presentation.uimodels.DeviceUiModel


@Composable
internal fun DeviceDetailsTopBar(
    device: DeviceUiModel,
    onNavigateUp: () -> Unit
){
    OneValetTopBar(
        labelResId = R.string.lbl_device_details_header,
        labelFormatArg = device.title,
        onNavigationIcon = onNavigateUp,
        navigationIcon = Icons.Default.ArrowBack,
        actions = {
            Icon(
                if (device.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                "favourite-indicator",
                tint =
                if (device.isFavorite) MaterialTheme.colors.onSurface
                else MaterialTheme.colors.onSurface,
            )
            Spacer(modifier = Modifier.width(8.0.dp))
        }
    )
}
