package app.onevalet.core.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar

@Composable
fun OneValetTopBar(
    @StringRes labelResId: Int,
    onNavigationIcon: () -> Unit,
    modifier: Modifier = Modifier,
    labelFormatArg: String? = null,
    navigationIcon: ImageVector = Icons.Default.Menu,
    actions: @Composable RowScope.() -> Unit = {},
) {

    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface.copy(
//            alpha = AppBarAlphas.translucentBarAlpha()
        ),
        contentColor = MaterialTheme.colors.onSurface,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyBottom = false,
        ),
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigationIcon) {
                Icon(navigationIcon, contentDescription = stringResource(labelResId))
            }
        },
        title = {
            Text(
                text = if (labelFormatArg.isNullOrEmpty()) stringResource(labelResId)
                else stringResource(
                    labelResId,
                    labelFormatArg,
                )
            )
        },
        actions = actions,
    )
}