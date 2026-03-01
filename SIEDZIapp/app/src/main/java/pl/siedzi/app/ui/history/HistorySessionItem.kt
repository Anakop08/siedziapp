package pl.siedzi.app.ui.history

data class HistorySessionItem(
    val sessionId: String,
    val fisheryName: String,
    val startTime: Long,
    val endTime: Long?,
    val catchCount: Int,
    val totalWeightKg: Double
)
