package pl.siedzi.app.ui.tackle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.TackleDictDao
import pl.siedzi.app.data.dao.TackleSetupDao
import pl.siedzi.app.data.dao.TimelineEntryDao
import pl.siedzi.app.data.entity.TackleDictItem
import pl.siedzi.app.data.entity.TackleSetup
import pl.siedzi.app.data.entity.TimelineEntry
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChangeSetupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val timelineEntryDao: TimelineEntryDao,
    private val tackleSetupDao: TackleSetupDao,
    private val tackleDictDao: TackleDictDao
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId") ?: ""

    private val _state = MutableStateFlow(TackleFormState())
    val state = _state.asStateFlow()

    private val _previousSetupTags = MutableStateFlow<List<String>>(emptyList())
    val previousSetupTags = _previousSetupTags.asStateFlow()

    private val _previousTime = MutableStateFlow("")
    val previousTime = _previousTime.asStateFlow()

    init {
        viewModelScope.launch {
            val lastCast = timelineEntryDao.getLastCastForSession(sessionId) ?: return@launch
            val setup = lastCast.tackleSetupId?.let { tackleSetupDao.getById(it) } ?: return@launch

            val timeFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            _previousTime.value = timeFormat.format(java.util.Date(lastCast.timestamp))

            val tags = mutableListOf<String>()
            listOf(setup.rodId, setup.reelId, setup.lineId, setup.baitId, setup.hookId).forEach { id ->
                id?.let { tackleDictDao.getById(it) }?.let {
                    tags.add("${it.name}${it.details?.let { " $it" } ?: ""}")
                }
            }
            _previousSetupTags.value = tags

            loadSetupIntoForm(setup)
        }
    }

    private suspend fun loadSetupIntoForm(setup: TackleSetup) {
        suspend fun loadField(id: String?, type: String) {
            id?.let { tackleDictDao.getById(it) }?.let { item ->
                val display = "${item.name}${item.details?.let { " · $it" } ?: ""}"
                _state.value = when (type) {
                    "rod" -> _state.value.copy(rodId = item.id, rodDisplay = display)
                    "reel" -> _state.value.copy(reelId = item.id, reelDisplay = display)
                    "line" -> _state.value.copy(lineId = item.id, lineDisplay = display)
                    "bait" -> _state.value.copy(baitId = item.id, baitDisplay = display)
                    "hook" -> _state.value.copy(hookId = item.id, hookDisplay = display)
                    else -> _state.value
                }
            }
        }
        loadField(setup.rodId, "rod")
        loadField(setup.reelId, "reel")
        loadField(setup.lineId, "line")
        loadField(setup.baitId, "bait")
        loadField(setup.hookId, "hook")
        _state.value = _state.value.copy(
            hasBoat = setup.hasBoat,
            hasEchosounder = setup.hasEchosounder
        )
    }

    fun updateRod(id: String?, display: String) {
        _state.value = _state.value.copy(rodId = id, rodDisplay = display)
    }

    fun updateReel(id: String?, display: String) {
        _state.value = _state.value.copy(reelId = id, reelDisplay = display)
    }

    fun updateLine(id: String?, display: String) {
        _state.value = _state.value.copy(lineId = id, lineDisplay = display)
    }

    fun updateBait(id: String?, display: String) {
        _state.value = _state.value.copy(baitId = id, baitDisplay = display)
    }

    fun updateHook(id: String?, display: String) {
        _state.value = _state.value.copy(hookId = id, hookDisplay = display)
    }

    fun updateGroundbait(display: String) {
        _state.value = _state.value.copy(groundbaitDisplay = display)
    }

    fun setHasBoat(value: Boolean) {
        _state.value = _state.value.copy(hasBoat = value)
    }

    fun setHasEchosounder(value: Boolean) {
        _state.value = _state.value.copy(hasEchosounder = value)
    }

    fun castAgain(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val s = _state.value
            val setupId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis()

            suspend fun ensureDictItem(type: String, id: String?, display: String): String? {
                if (display.isBlank()) return null
                if (id != null) return id
                val newId = UUID.randomUUID().toString()
                tackleDictDao.insert(
                    TackleDictItem(
                        id = newId,
                        type = type,
                        name = display,
                        details = null,
                        createdAt = timestamp
                    )
                )
                return newId
            }

            val rodId = ensureDictItem("rod", s.rodId, s.rodDisplay)
            val reelId = ensureDictItem("reel", s.reelId, s.reelDisplay)
            val lineId = ensureDictItem("line", s.lineId, s.lineDisplay)
            val baitId = ensureDictItem("bait", s.baitId, s.baitDisplay)
            val hookId = ensureDictItem("hook", s.hookId, s.hookDisplay)

            val customJson = if (s.groundbaitDisplay.isNotBlank()) {
                """{"groundbait":"${s.groundbaitDisplay.replace("\"", "\\\"")}"}"""
            } else null

            val setup = TackleSetup(
                id = setupId,
                rodId = rodId,
                reelId = reelId,
                lineId = lineId,
                baitId = baitId,
                hookId = hookId,
                hasBoat = s.hasBoat,
                hasEchosounder = s.hasEchosounder,
                customJson = customJson
            )
            tackleSetupDao.insert(setup)

            val entryId = UUID.randomUUID().toString()
            timelineEntryDao.insert(
                TimelineEntry(
                    id = entryId,
                    sessionId = sessionId,
                    tackleSetupId = setupId,
                    timestamp = timestamp,
                    type = "cast",
                    catchId = null
                )
            )
            onSuccess()
        }
    }
}
