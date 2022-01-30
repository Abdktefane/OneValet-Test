package app.onevalet.core.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.onevalet.R
import app.onevalet.application.navigations.Screen


@Composable
internal fun OneValetDrawer(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = Modifier.background(MaterialTheme.colors.surface)
    ) {

        DrawerItems.forEach { item ->

            DrawerItemCompose(
                item = item,
                selected = selectedNavigation == item.screen,
                onClick = { onNavigationSelected(item.screen) },
            )
        }
        // Header
    }

}

@Composable
private fun DrawerItemCompose(
    item: DrawerItem.ImageVectorIcon,
    selected: Boolean,
    onClick: (DrawerItem) -> Unit
) {
    val background = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val onBackgroundColor =
        if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(item) })
            .height(45.dp)
            .background(background)
            .padding(start = 16.dp)
    ) {
        Image(
            imageVector = if (selected) item.selectedImageVector else item.selectedImageVector,
            contentDescription = stringResource(item.contentDescriptionResId),
            colorFilter = ColorFilter.tint(onBackgroundColor),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = stringResource(item.labelResId),
            fontSize = 18.sp,
            color = onBackgroundColor
        )
    }
}

private sealed class DrawerItem(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    class ResourceIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null,
    ) : DrawerItem(screen, labelResId, contentDescriptionResId)

    class ImageVectorIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector,
    ) : DrawerItem(screen, labelResId, contentDescriptionResId)
}

private val DrawerItems = listOf(
    DrawerItem.ImageVectorIcon(
        screen = Screen.Home,
        labelResId = R.string.lbl_home,
        contentDescriptionResId = R.string.lbl_home,
        iconImageVector = Icons.Outlined.Home,
        selectedImageVector = Icons.Default.Home,
    ),
    DrawerItem.ImageVectorIcon(
        screen = Screen.Devices,
        labelResId = R.string.lbl_my_devices,
        contentDescriptionResId = R.string.lbl_home,
        iconImageVector = Icons.Outlined.Devices,
        selectedImageVector = Icons.Default.Devices,
    ),
    DrawerItem.ImageVectorIcon(
        screen = Screen.Settings,
        labelResId = R.string.lbl_settings,
        contentDescriptionResId = R.string.lbl_settings,
        iconImageVector = Icons.Outlined.Settings,
        selectedImageVector = Icons.Default.Settings,
    ),
)
