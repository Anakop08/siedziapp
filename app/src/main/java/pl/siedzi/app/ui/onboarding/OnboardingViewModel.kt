package pl.siedzi.app.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.AppMetaDao
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appMetaDao: AppMetaDao
) : ViewModel() {

    fun completeOnboarding() {
        viewModelScope.launch {
            appMetaDao.setOnboardingCompleted(true)
        }
    }
}
