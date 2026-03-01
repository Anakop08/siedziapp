package pl.siedzi.app.ui.fishery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.entity.Fishery
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FisheryFormViewModel @Inject constructor(
    private val fisheryDao: FisheryDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val fisheryId = savedStateHandle.get<String>("fisheryId") ?: "new"

    private val _fishery = MutableStateFlow<Fishery?>(null)
    val fishery = _fishery.asStateFlow()

    init {
        if (fisheryId != "new") {
            viewModelScope.launch {
                _fishery.value = fisheryDao.getById(fisheryId)
            }
        }
    }

    fun save(
        name: String,
        address: String,
        lat: Double,
        lon: Double,
        stationNote: String,
        topoClipUri: String?,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val id = if (fisheryId == "new") UUID.randomUUID().toString() else fisheryId
            val fishery = Fishery(
                id = id,
                name = name,
                address = address.ifBlank { null },
                lat = lat,
                lon = lon,
                stationNote = stationNote.ifBlank { null },
                speciesIdsJson = null,
                topoClipUri = topoClipUri?.takeIf { it.isNotBlank() },
                createdAt = _fishery.value?.createdAt ?: System.currentTimeMillis()
            )
            fisheryDao.insert(fishery)
            onSaved()
        }
    }
}
