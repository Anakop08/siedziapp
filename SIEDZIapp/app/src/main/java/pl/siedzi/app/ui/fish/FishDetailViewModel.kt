package pl.siedzi.app.ui.fish

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.entity.FishSpecies
import javax.inject.Inject

@HiltViewModel
class FishDetailViewModel @Inject constructor(
    private val fishSpeciesDao: FishSpeciesDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val speciesId = savedStateHandle.get<String>("speciesId") ?: ""

    private val _species = MutableStateFlow<FishSpecies?>(null)
    val species = _species.asStateFlow()

    init {
        viewModelScope.launch {
            _species.value = fishSpeciesDao.getById(speciesId)
        }
    }
}
