package pl.siedzi.app.data

/**
 * Tymczasowe przechowanie URI zdjęć przy przejściu z CatchPhoto do CatchCard.
 */
object PendingCatchPhotos {
    @Volatile
    var uris: List<String> = emptyList()
        private set

    fun set(uris: List<String>) {
        this.uris = uris
    }

    fun consume(): List<String> {
        val result = uris
        uris = emptyList()
        return result
    }
}
