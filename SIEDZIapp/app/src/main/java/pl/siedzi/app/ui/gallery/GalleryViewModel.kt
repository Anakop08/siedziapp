package pl.siedzi.app.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import pl.siedzi.app.data.dao.CatchDao
import pl.siedzi.app.data.dao.FishSpeciesDao
import javax.inject.Inject

data class GalleryPhotoItem(
    val uri: String,
    val catchId: String,
    val speciesName: String?,
    val weightKg: Double,
    val timestamp: Long
)

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val catchDao: CatchDao,
    private val fishSpeciesDao: FishSpeciesDao
) : ViewModel() {

    val photoItems = catchDao.getCatchesWithPhotos()
        .flatMapLatest { catches ->
            flow {
                val items = catches.flatMap { catch ->
                    val species = fishSpeciesDao.getById(catch.speciesId)
                    catch.photoUris.map { uri ->
                        GalleryPhotoItem(
                            uri = uri,
                            catchId = catch.id,
                            speciesName = species?.name,
                            weightKg = catch.weightKg,
                            timestamp = catch.timestamp
                        )
                    }
                }
                emit(items)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
