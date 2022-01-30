package app.onevalet.application

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.ViewModelProvider
import app.onevalet.core.ui.OneValetActivity
import app.onevalet.core.ui.theme.OneValetChallengeTheme
import app.onevalet.application.presentation.page.App
import app.onevalet.application.presentation.viewmodels.MainActivityViewModel
import app.onevalet.core.extensions.shouldUseDarkColors
import app.onevalet.core.settings.OneValetPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : OneValetActivity() {

    private lateinit var viewModel: MainActivityViewModel
    @Inject internal lateinit var preferences: OneValetPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        setContent {
            OneValetChallengeTheme(useDarkColors = preferences.shouldUseDarkColors()) {
                App()
            }
        }
    }


}

