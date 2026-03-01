package pl.siedzi.app.navigation

sealed class SiedziRoute(val path: String) {
    data object Splash : SiedziRoute("splash")
    data object Onboarding : SiedziRoute("onboarding")
    data object Dashboard : SiedziRoute("dashboard")
    data object Disclaimer : SiedziRoute("disclaimer")
    data object FishList : SiedziRoute("fish_list")
    data object FishDetail : SiedziRoute("fish_detail/{speciesId}") {
        fun create(speciesId: String) = "fish_detail/$speciesId"
    }
    // Placeholder routes (Faza 2+)
    data object Wspomnienia : SiedziRoute("wspomnienia")
    data object Planowanie : SiedziRoute("planowanie")
    data object Sesja : SiedziRoute("sesja")
    data object Galeria : SiedziRoute("galeria")
    data object Ustawienia : SiedziRoute("ustawienia")
    data object Historia : SiedziRoute("historia")
    data object FisheryList : SiedziRoute("fishery_list")
    data object FisheryForm : SiedziRoute("fishery_form/{fisheryId}") {
        fun create(fisheryId: String = "new") = "fishery_form/$fisheryId"
    }
}
