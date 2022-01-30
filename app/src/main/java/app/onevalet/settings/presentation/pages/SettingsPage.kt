package app.onevalet.settings.presentation.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.onevalet.R
import app.onevalet.core.extensions.rememberFlowWithLifecycle
import app.onevalet.core.settings.OneValetPreferences
import app.onevalet.core.ui.composables.OneValetTopBar
import app.onevalet.devices.presentation.viewmodel.DevicesState
import app.onevalet.devices.presentation.viewmodel.DevicesViewModel
import app.onevalet.settings.presentation.viewmodel.SettingsState
import app.onevalet.settings.presentation.viewmodel.SettingsViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsPage(toggleContainer: () -> Unit) {
    SettingsPage(
        viewModel = hiltViewModel(),
        toggleContainer = toggleContainer,
    )
}

@ExperimentalComposeUiApi
@Composable
private fun SettingsPage(
    viewModel: SettingsViewModel,
    toggleContainer: () -> Unit,

    ) {
    val viewState by rememberFlowWithLifecycle(viewModel.state)
        .collectAsState(initial = SettingsState.Empty)

    DevicesPage(
        state = viewState,
        toggleContainer = toggleContainer,
        onThemeChange = viewModel::changeTheme,
    )

}

@ExperimentalComposeUiApi
@Composable
private fun DevicesPage(
    state: SettingsState,
    toggleContainer: () -> Unit,
    onThemeChange: (OneValetPreferences.Theme) -> Unit,

    ) {

    Scaffold(
        topBar = {
            OneValetTopBar(
                labelResId = R.string.lbl_settings,
                onNavigationIcon = toggleContainer,
            )
        }
    ) {
        Column(Modifier.padding(8.0.dp)){
            Text(stringResource(id = R.string.lbl_theme), style = MaterialTheme.typography.subtitle1)
            Column {
                OneValetPreferences.Theme.values().forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(4.0.dp)
                    ) {
                        RadioButton(
                            selected = state.selectedTheme == it,
                            onClick = { onThemeChange(it) },
                        )
                        Text(it.name.lowercase(), modifier = Modifier.padding(start = 4.0.dp))
                    }

                }
            }

        }


    }

}