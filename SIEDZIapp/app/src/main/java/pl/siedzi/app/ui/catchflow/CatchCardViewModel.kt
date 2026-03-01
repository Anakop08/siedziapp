package pl.siedzi.app.ui.catchflow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.siedzi.app.data.PendingCatchPhotos
import pl.siedzi.app.data.dao.CatchDao
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.dao.TackleDictDao
import pl.siedzi.app.data.dao.TackleSetupDao
import pl.siedzi.app.data.dao.TimelineEntryDao
import pl.siedzi.app.data.entity.FishCatch
import pl.siedzi.app.data.entity.FishSpecies
import pl.siedzi.app.data.entity.TackleSetup
import pl.siedzi.app.data.entity.TimelineEntry
import pl.siedzi.app.service.CatchMetadataService
import java.util.UUID
import javax.inject.Inject

data class CatchCardState(
    val photoUris: List<String> = emptyList(),
    val speciesId: String? = null,
    val speciesName: String = "",
    val weightKg: Double? = null,
    val weightText: String = "",
    val lengthCm: Int? = null,
    val lengthText: String = "",
    val nickname: String = "",
    val tackleSetupId: String? = null,
    val tackleSetupTags: List<String> = emptyList(),
    val gpsText: String = "",
    val timeText: String = "",
    val lat: Double? = null,
    val lon: Double? = null
)

@HiltViewModel
class CatchCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val catchDao: CatchDao,
    private val timelineEntryDao: TimelineEntryDao,
    private val tackleSetupDao: TackleSetupDao,
    private val tackleDictDao: TackleDictDao,
    private val fishSpeciesDao: FishSpeciesDao,
    private val metadataService: CatchMetadataService
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId") ?: ""

    private val _state = MutableStateFlow(CatchCardState())
    val state = _state.asStateFlow()

    val speciesList = fishSpeciesDao.getAll()

    init {
        viewModelScope.launch {
            loadTackleAndMeta(PendingCatchPhotos.consume())
        }
    }

    fun setPhotoUris(uris: List<String>) {
        _state.value = _state.value.copy(photoUris = uris)
    }

    fun setSpecies(species: FishSpecies?) {
        _state.value = _state.value.copy(
            speciesId = species?.id,
            speciesName = species?.name ?: ""
        )
    }

    fun setWeight(text: String) {
        _state.value = _state.value.copy(
            weightText = text,
            weightKg = text.replace(",", ".").toDoubleOrNull()
        )
    }

    fun setLength(text: String) {
        _state.value = _state.value.copy(
            lengthText = text,
            lengthCm = text.toIntOrNull()
        )
    }

    fun setNickname(text: String) {
        _state.value = _state.value.copy(nickname = text)
    }

    fun loadTackleAndMeta(photoUris: List<String>) {
        viewModelScope.launch {
            setPhotoUris(photoUris)
            val lastCast = timelineEntryDao.getLastCastForSession(sessionId)
            val setup = if (lastCast?.tackleSetupId != null) {
                tackleSetupDao.getById(lastCast.tackleSetupId!!)
            } else null

            val setupId: String
            val tags: List<String>
            if (setup != null) {
                setupId = setup.id
                tags = buildList {
                    listOf(setup.rodId, setup.reelId, setup.lineId, setup.baitId, setup.hookId).forEach { id ->
                        id?.let { tackleDictDao.getById(it) }?.let {
                            add("${it.name}${it.details?.let { " $it" } ?: ""}")
                        }
                    }
                }
            } else {
                setupId = UUID.randomUUID().toString()
                tackleSetupDao.insert(TackleSetup(id = setupId))
                tags = emptyList()
            }

            val meta = metadataService.getMetadata(setupId)
            val timeFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            _state.value = _state.value.copy(
                tackleSetupId = setupId,
                tackleSetupTags = tags,
                timeText = timeFormat.format(java.util.Date(meta.timestamp)),
                gpsText = meta.gpsFormatted,
                lat = meta.lat,
                lon = meta.lon
            )
        }
    }

    fun saveCatch(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val s = _state.value
            val weight = s.weightKg ?: return@launch
            val speciesId = s.speciesId ?: return@launch
            val tackleSetupId = s.tackleSetupId ?: return@launch

            val catchId = UUID.randomUUID().toString()
            val entryId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis()

            val photoUrisJson = s.photoUris.take(5).joinToString(",")
                .takeIf { it.isNotBlank() } ?: null

            val catchEntity = FishCatch(
                id = catchId,
                sessionId = sessionId,
                timelineEntryId = entryId,
                speciesId = speciesId,
                weightKg = weight,
                lengthCm = s.lengthCm,
                nickname = s.nickname.takeIf { it.isNotBlank() },
                lat = s.lat,
                lon = s.lon,
                timestamp = timestamp,
                weatherSnapshot = null,
                solunarSnapshot = null,
                tackleSetupId = tackleSetupId,
                photoUrisJson = photoUrisJson
            )
            catchDao.insert(catchEntity)

            val entry = TimelineEntry(
                id = entryId,
                sessionId = sessionId,
                tackleSetupId = tackleSetupId,
                timestamp = timestamp,
                type = "catch",
                catchId = catchId
            )
            timelineEntryDao.insert(entry)

            onSuccess()
        }
    }

    fun getSessionId() = sessionId
}
