package pl.siedzi.app.ui.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SessionDao
import pl.siedzi.app.data.dao.TripDao
import pl.siedzi.app.data.entity.Fishery
import pl.siedzi.app.data.entity.Session
import pl.siedzi.app.data.entity.Trip
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlanningViewModel @Inject constructor(
    private val fisheryDao: FisheryDao,
    private val tripDao: TripDao,
    private val sessionDao: SessionDao
) : ViewModel() {

    private val _selectedFishery = MutableStateFlow<Fishery?>(null)
    val selectedFishery = _selectedFishery.asStateFlow()

    private val _startDate = MutableStateFlow(System.currentTimeMillis())
    val startDate = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow(System.currentTimeMillis())
    val endDate = _endDate.asStateFlow()

    private val _strategyNote = MutableStateFlow("")
    val strategyNote = _strategyNote.asStateFlow()

    private val _savedTrip = MutableStateFlow<Trip?>(null)
    val savedTrip = _savedTrip.asStateFlow()

    fun setSelectedFisheryId(fisheryId: String) {
        viewModelScope.launch {
            _selectedFishery.value = fisheryDao.getById(fisheryId)
        }
    }

    fun setStartDate(millis: Long) { _startDate.value = millis }
    fun setEndDate(millis: Long) { _endDate.value = millis }
    fun setStrategyNote(note: String) { _strategyNote.value = note }

    fun saveTrip(onSuccess: (Trip) -> Unit) {
        viewModelScope.launch {
            val fishery = _selectedFishery.value
            if (fishery == null) return@launch
            val start = _startDate.value
            var end = _endDate.value
            if (end < start) {
                end = start
                _endDate.value = start
            }
            val trip = Trip(
                id = UUID.randomUUID().toString(),
                fisheryId = fishery.id,
                startDate = start,
                endDate = end,
                strategyNote = _strategyNote.value.takeIf { it.isNotBlank() },
                createdAt = System.currentTimeMillis()
            )
            tripDao.insert(trip)
            _savedTrip.value = trip
            onSuccess(trip)
        }
    }

    fun startSession(trip: Trip, onSuccess: (Session) -> Unit) {
        viewModelScope.launch {
            val existing = sessionDao.getActiveSession()
            if (existing != null) {
                onSuccess(existing)
                return@launch
            }
            val session = Session(
                id = UUID.randomUUID().toString(),
                tripId = trip.id,
                fisheryId = trip.fisheryId,
                startTime = System.currentTimeMillis(),
                endTime = null,
                lat = null,
                lon = null,
                isActive = true
            )
            sessionDao.insert(session)
            onSuccess(session)
        }
    }

    fun getActiveSession() = viewModelScope.launch {
        sessionDao.getActiveSessionFlow().first()
    }
}
