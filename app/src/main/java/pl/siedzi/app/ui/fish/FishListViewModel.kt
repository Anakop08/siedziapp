package pl.siedzi.app.ui.fish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.entity.FishSpecies
import javax.inject.Inject

@HiltViewModel
class FishListViewModel @Inject constructor(
    private val fishSpeciesDao: FishSpeciesDao
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val species = combine(
        fishSpeciesDao.getAll(),
        _searchQuery
    ) { all, query ->
        if (query.isBlank()) all
        else all.filter {
            it.name.contains(query, ignoreCase = true) ||
                (it.nameLatin?.contains(query, ignoreCase = true) == true)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchChange(query: String) {
        _searchQuery.value = query
    }
}
