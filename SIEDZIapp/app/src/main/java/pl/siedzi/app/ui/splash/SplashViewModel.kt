package pl.siedzi.app.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.AppMetaDao
import javax.inject.Inject

sealed class SplashDestination {
    data object Onboarding : SplashDestination()
    data object Dashboard : SplashDestination()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appMetaDao: AppMetaDao
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination = _destination.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            val meta = appMetaDao.get()
            _destination.value = if (meta?.onboardingCompleted == true) {
                SplashDestination.Dashboard
            } else {
                SplashDestination.Onboarding
            }
        }
    }
}
