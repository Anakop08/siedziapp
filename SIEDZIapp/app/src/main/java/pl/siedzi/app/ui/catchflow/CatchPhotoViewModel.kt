package pl.siedzi.app.ui.catchflow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val MAX_PHOTOS = 5

@HiltViewModel
class CatchPhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sessionId: String = savedStateHandle.get<String>("sessionId") ?: ""

    private val _photoUris = MutableStateFlow<List<String>>(emptyList())
    val photoUris = _photoUris.asStateFlow()

    val canAddMore: Boolean
        get() = _photoUris.value.size < MAX_PHOTOS

    fun addPhotos(uris: List<String>) {
        val current = _photoUris.value
        val toAdd = uris.take(MAX_PHOTOS - current.size)
        if (toAdd.isNotEmpty()) {
            _photoUris.update { it + toAdd }
        }
    }

    fun removePhoto(index: Int) {
        _photoUris.update { it.filterIndexed { i, _ -> i != index } }
    }

    fun getSessionId() = sessionId
}
