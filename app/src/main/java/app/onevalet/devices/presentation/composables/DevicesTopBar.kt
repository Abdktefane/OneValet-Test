package app.onevalet.devices.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import app.onevalet.R
import app.onevalet.core.ui.composables.OneValetTopBar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun DevicesTopBar(
    toggleContainer: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    searchQuery: String = ""
) {

    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(showClearButton) {
        if (showClearButton)
            focusRequester.requestFocus()
    }


    OneValetTopBar(
        labelResId = R.string.lbl_devices,
        onNavigationIcon = toggleContainer,
        actions = {
            if (showClearButton)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .focusRequester(focusRequester),
                    value = searchQuery,
                    onValueChange = onSearchTextChanged,
                    maxLines = 1,
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.lbl_devices_search)) },

                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    ),
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = showClearButton,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            IconButton(onClick = {
                                showClearButton = false
                                onSearchTextChanged("")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "clear"
                                )
                            }

                        }
                    },

                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                )
            else
                IconButton(onClick = {
                    showClearButton = true
                }) {
                    Icon(Icons.Default.Search, "Search")
                    LocalFocusManager.current.moveFocus(FocusDirection.In)

                }


        }
    )

}

