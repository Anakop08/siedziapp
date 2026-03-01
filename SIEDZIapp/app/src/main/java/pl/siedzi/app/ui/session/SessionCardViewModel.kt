package pl.siedzi.app.ui.session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.CatchDao
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SessionDao
import pl.siedzi.app.data.entity.FishCatch
import pl.siedzi.app.data.entity.Session
import javax.inject.Inject

data class SessionCardState(
    val fisheryName: String = "",
    val startTime: Long = 0L,
    val endTime: Long? = null,
    val catchCount: Int = 0,
    val totalWeightKg: Double = 0.0,
    val speciesIds: List<String> = emptyList(),
    val speciesNames: Map<String, String> = emptyMap()
)

@HiltViewModel
class SessionCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionDao: SessionDao,
    private val fisheryDao: FisheryDao,
    private val catchDao: CatchDao,
    private val fishSpeciesDao: FishSpeciesDao
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId") ?: ""

    private val _state = MutableStateFlow(SessionCardState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val session = sessionDao.getById(sessionId) ?: return@launch
            val fishery = fisheryDao.getById(session.fisheryId)
            val catches = catchDao.getBySessionId(sessionId).first()

            val weight = catches.sumOf { it.weightKg }
            val speciesIds = catches.map { it.speciesId }.distinct()
            val speciesNames = mutableMapOf<String, String>()
            speciesIds.forEach { id ->
                fishSpeciesDao.getById(id)?.let { speciesNames[id] = it.name }
            }

            _state.value = SessionCardState(
                fisheryName = fishery?.name ?: "",
                startTime = session.startTime,
                endTime = session.endTime,
                catchCount = catches.size,
                totalWeightKg = weight,
                speciesIds = speciesIds,
                speciesNames = speciesNames
            )
        }
    }

    fun getSessionId() = sessionId
}
