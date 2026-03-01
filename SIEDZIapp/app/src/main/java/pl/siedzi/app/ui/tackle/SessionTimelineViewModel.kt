package pl.siedzi.app.ui.tackle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.CatchDao
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SessionDao
import pl.siedzi.app.data.dao.TackleSetupDao
import pl.siedzi.app.data.dao.TimelineEntryDao
import pl.siedzi.app.data.dao.TackleDictDao
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.entity.FishCatch
import pl.siedzi.app.data.entity.TimelineEntry
import pl.siedzi.app.data.entity.TackleSetup
import javax.inject.Inject

data class TimelineEntryUi(
    val entry: TimelineEntry,
    val setup: TackleSetup?,
    val setupTags: List<String>,
    val catchSpecies: String? = null,
    val catchWeight: String? = null
)

@HiltViewModel
class SessionTimelineViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val timelineEntryDao: TimelineEntryDao,
    private val tackleSetupDao: TackleSetupDao,
    private val tackleDictDao: TackleDictDao,
    private val catchDao: CatchDao,
    private val fishSpeciesDao: FishSpeciesDao,
    private val sessionDao: SessionDao,
    private val fisheryDao: FisheryDao
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId") ?: ""

    private val _fisheryName = MutableStateFlow("")
    val fisheryName = _fisheryName.asStateFlow()

    val entries = timelineEntryDao.getBySessionId(sessionId).map { entries ->
        val result = mutableListOf<TimelineEntryUi>()
            for (entry in entries) {
                val setup = entry.tackleSetupId?.let { tackleSetupDao.getById(it) }
                val tags = mutableListOf<String>()
                setup?.let { s ->
                    listOf(s.rodId, s.reelId, s.lineId, s.baitId, s.hookId).forEach { id ->
                        id?.let { tackleDictDao.getById(it) }?.let {
                            tags.add("${it.name}${it.details?.let { " $it" } ?: ""}")
                        }
                    }
                }
                var catchSpecies: String? = null
                var catchWeight: String? = null
                entry.catchId?.let { catchId ->
                    catchDao.getById(catchId)?.let { c ->
                        catchSpecies = fishSpeciesDao.getById(c.speciesId)?.name
                        catchWeight = "${c.weightKg} kg" + (c.lengthCm?.let { " · $it cm" } ?: "")
                    }
                }
                result.add(
                    TimelineEntryUi(
                        entry = entry,
                        setup = setup,
                        setupTags = tags,
                        catchSpecies = catchSpecies,
                        catchWeight = catchWeight
                    )
                )
            }
        result
    }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            val session = sessionDao.getById(sessionId)
            session?.let {
                _fisheryName.value = fisheryDao.getById(it.fisheryId)?.name ?: ""
            }
        }
    }
}
