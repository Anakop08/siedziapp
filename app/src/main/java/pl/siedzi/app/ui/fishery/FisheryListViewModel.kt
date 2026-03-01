package pl.siedzi.app.ui.fishery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import pl.siedzi.app.data.FisheryImporter
import pl.siedzi.app.data.FisheryJsonImporter
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.entity.Fishery
import javax.inject.Inject

@HiltViewModel
class FisheryListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fisheryDao: FisheryDao
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val fisheries = combine(
        fisheryDao.getAll(),
        _searchQuery
    ) { all, query ->
        if (query.isBlank()) all
        else all.filter {
            it.name.contains(query, ignoreCase = true) ||
                (it.address?.contains(query, ignoreCase = true) == true)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchChange(query: String) {
        _searchQuery.value = query
    }

    fun importSample() {
        viewModelScope.launch {
            try {
                context.assets.open("fisheries_sample.csv").use { stream ->
                    FisheryImporter.importFromCsv(context, stream, fisheryDao)
                }
            } catch (_: Exception) { }
        }
    }

    fun importFromJson() {
        viewModelScope.launch {
            try {
                FisheryJsonImporter.importFromAssets(context, fisheryDao)
            } catch (_: Exception) { }
        }
    }
}
