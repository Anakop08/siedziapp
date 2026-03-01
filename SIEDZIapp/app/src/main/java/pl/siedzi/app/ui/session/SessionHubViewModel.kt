package pl.siedzi.app.ui.session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SessionDao
import pl.siedzi.app.data.entity.Session
import javax.inject.Inject

@HiltViewModel
class SessionHubViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionDao: SessionDao,
    private val fisheryDao: FisheryDao
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId") ?: ""

    private val _fisheryName = MutableStateFlow("")
    val fisheryName = _fisheryName.asStateFlow()

    private val _session = MutableStateFlow<Session?>(null)
    val session = _session.asStateFlow()

    init {
        viewModelScope.launch {
            val s = sessionDao.getById(sessionId)
            _session.value = s
            s?.let {
                _fisheryName.value = fisheryDao.getById(it.fisheryId)?.name ?: ""
            }
        }
    }

    fun endSession(onEnded: () -> Unit) {
        viewModelScope.launch {
            sessionDao.endSession(sessionId, System.currentTimeMillis())
            onEnded()
        }
    }
}
