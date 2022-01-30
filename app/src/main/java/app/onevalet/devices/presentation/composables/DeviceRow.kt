package app.onevalet.devices.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.onevalet.R
import app.onevalet.devices.presentation.uimodels.DeviceUiModel

@Composable
internal fun DeviceRow(
    openDeviceDetails: () -> Unit,
    device: DeviceUiModel
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = openDeviceDetails)
            .padding(vertical = 6.0.dp)
            .border(
                1.dp,
                color = MaterialTheme.colors.onSurface,
                shape = RoundedCornerShape(4.0.dp)
            )
            .padding(vertical = 8.0.dp)
    ) {

        Icon(
            Icons.Default.PermDeviceInformation, "device image",
            Modifier
                .padding(8.0.dp)
                .size(40.dp)
        )

        Column {
            Text(device.title)
            Spacer(modifier = Modifier.height(4.0.dp))
            Text(
                stringResource(R.string.lbl_device_price, device.price),
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            )
        }
        Spacer(
            modifier = Modifier
                .width(8.0.dp)
                .weight(2f)
        )
        Icon(
            Icons.Default.Info, "info icon",
            Modifier
                .padding(8.0.dp)
                .size(32.dp)
        )

    }
}
