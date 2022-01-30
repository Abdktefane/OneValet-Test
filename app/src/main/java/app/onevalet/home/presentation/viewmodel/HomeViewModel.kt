package app.onevalet.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import app.onevalet.core.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val logger: Logger,
) : ViewModel() {

    init {
        logger.i("home view model initiated");
    }
}
