package pl.siedzi.app.service

data class CatchMetadata(
    val lat: Double?,
    val lon: Double?,
    val timestamp: Long,
    val weatherSnapshot: String?,
    val solunarSnapshot: String?,
    val tackleSetupId: String
) {
    val gpsFormatted: String
        get() = when {
            lat != null && lon != null -> "%.5f, %.5f".format(lat, lon)
            else -> ""
        }
}
