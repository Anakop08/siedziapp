package pl.siedzi.app.ui.session

/**
 * Statystyki sesji – waga, długość, ilość.
 * Używane przez SessionCardViewModel na Karcie Sesji (Faza 4.1.2).
 */
data class SessionStats(
    val catchCount: Int,
    val totalWeightKg: Double,
    val maxLengthCm: Int?
)
