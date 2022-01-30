package app.onevalet.devicedetails.presentation.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.onevalet.R
import app.onevalet.devicedetails.presentation.composables.DeviceDetailsTopBar
import app.onevalet.devices.presentation.uimodels.DeviceUiModel
import coil.compose.rememberImagePainter


@Composable
fun DevicesDetailsPage(
    navigateUp: () -> Unit,
    device: DeviceUiModel
) {

    Scaffold(
        topBar = { DeviceDetailsTopBar(device, navigateUp) }
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.0.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberImagePainter(
                        "https://picsum.photos/id/237/200/100",
                        builder = { crossfade(true) }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.aspectRatio(16 / 9f).fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.0.dp))
            listOf(
                stringResource(id = R.string.lbl_name_details, device.title),
                stringResource(id = R.string.lbl_type_details, device.type),
                stringResource(id = R.string.lbl_price_details, device.price),
                stringResource(id = R.string.lbl_description_details, device.Description),
            ).forEach { text ->
                Text(text, Modifier.padding(top = 8.0.dp))
            }

        }

    }
}