package pl.siedzi.app.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import pl.siedzi.app.data.dao.CatchDao
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SessionDao
import javax.inject.Inject

@HiltViewModel
class HistoryListViewModel @Inject constructor(
    private val sessionDao: SessionDao,
    private val fisheryDao: FisheryDao,
    private val catchDao: CatchDao
) : ViewModel() {

    val historyItems = sessionDao.getEndedSessions()
        .flatMapLatest { sessions ->
            flow {
                val items = sessions.map { session ->
                    val fishery = fisheryDao.getById(session.fisheryId)
                    val catches = catchDao.getBySessionId(session.id).first()
                    HistorySessionItem(
                        sessionId = session.id,
                        fisheryName = fishery?.name ?: "",
                        startTime = session.startTime,
                        endTime = session.endTime,
                        catchCount = catches.size,
                        totalWeightKg = catches.sumOf { it.weightKg }
                    )
                }
                emit(items)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
