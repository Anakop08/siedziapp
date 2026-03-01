package pl.siedzi.app.ui.tackle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SessionDao
import pl.siedzi.app.data.dao.TackleDictDao
import pl.siedzi.app.data.dao.TackleSetupDao
import pl.siedzi.app.data.dao.TimelineEntryDao
import pl.siedzi.app.data.entity.TackleDictItem
import pl.siedzi.app.data.entity.TackleSetup
import pl.siedzi.app.data.entity.TimelineEntry
import java.util.UUID
import javax.inject.Inject

data class TackleFormState(
    val rodId: String? = null,
    val rodDisplay: String = "",
    val reelId: String? = null,
    val reelDisplay: String = "",
    val lineId: String? = null,
    val lineDisplay: String = "",
    val baitId: String? = null,
    val baitDisplay: String = "",
    val hookId: String? = null,
    val hookDisplay: String = "",
    val groundbaitDisplay: String = "", // zanęta - w customJson
    val hasBoat: Boolean = false,
    val hasEchosounder: Boolean = false
)

@HiltViewModel
class TackleFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tackleDictDao: TackleDictDao,
    private val tackleSetupDao: TackleSetupDao,
    private val timelineEntryDao: TimelineEntryDao,
    private val sessionDao: SessionDao,
    private val fisheryDao: FisheryDao
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId") ?: ""

    private val _fisheryName = MutableStateFlow("")
    val fisheryName = _fisheryName.asStateFlow()

    init {
        viewModelScope.launch {
            val session = sessionDao.getById(sessionId)
            session?.let {
                _fisheryName.value = fisheryDao.getById(it.fisheryId)?.name ?: ""
            }
        }
    }

    private val _state = MutableStateFlow(TackleFormState())
    val state = _state.asStateFlow()

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

    fun loadFromPreviousSetup(previousSetup: TackleSetup) {
        viewModelScope.launch {
            listOf(
                "rod" to previousSetup.rodId,
                "reel" to previousSetup.reelId,
                "line" to previousSetup.lineId,
                "bait" to previousSetup.baitId,
                "hook" to previousSetup.hookId
            ).forEach { (type, id) ->
                id?.let { tackleDictDao.getById(it) }?.let { item ->
                    when (type) {
                        "rod" -> _state.value = _state.value.copy(
                            rodId = item.id,
                            rodDisplay = "${item.name}${item.details?.let { " · $it" } ?: ""}"
                        )
                        "reel" -> _state.value = _state.value.copy(
                            reelId = item.id,
                            reelDisplay = "${item.name}${item.details?.let { " · $it" } ?: ""}"
                        )
                        "line" -> _state.value = _state.value.copy(
                            lineId = item.id,
                            lineDisplay = "${item.name}${item.details?.let { " · $it" } ?: ""}"
                        )
                        "bait" -> _state.value = _state.value.copy(
                            baitId = item.id,
                            baitDisplay = "${item.name}${item.details?.let { " · $it" } ?: ""}"
                        )
                        "hook" -> _state.value = _state.value.copy(
                            hookId = item.id,
                            hookDisplay = "${item.name}${item.details?.let { " · $it" } ?: ""}"
                        )
                    }
                }
            }
        }
    }

    fun castRod(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val s = _state.value
            val setupId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis()

            suspend fun ensureDictItem(type: String, id: String?, display: String): String? {
                if (display.isBlank()) return null
                val existingId = when (type) {
                    "rod" -> s.rodId
                    "reel" -> s.reelId
                    "line" -> s.lineId
                    "bait" -> s.baitId
                    "hook" -> s.hookId
                    else -> null
                }
                if (existingId != null) return existingId
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
