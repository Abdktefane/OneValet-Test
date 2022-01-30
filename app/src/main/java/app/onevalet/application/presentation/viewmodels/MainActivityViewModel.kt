package app.onevalet.application.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


// Application ViewModel For General Use Cases
@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {

}
