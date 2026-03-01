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
    data object MapCrop : SiedziRoute("map_crop/{lat}/{lon}") {
        fun create(lat: Double, lon: Double) = "map_crop/$lat/$lon"
    }
    data object PlanningSelectFishery : SiedziRoute("planning_select_fishery")
    data object CheckIn : SiedziRoute("check_in/{sessionId}") {
        fun create(sessionId: String) = "check_in/$sessionId"
    }
    data object SessionHub : SiedziRoute("session_hub/{sessionId}") {
        fun create(sessionId: String) = "session_hub/$sessionId"
    }
    data object TackleForm : SiedziRoute("tackle_form/{sessionId}") {
        fun create(sessionId: String) = "tackle_form/$sessionId"
    }
    data object SessionTimeline : SiedziRoute("session_timeline/{sessionId}") {
        fun create(sessionId: String) = "session_timeline/$sessionId"
    }
    data object ChangeSetup : SiedziRoute("change_setup/{sessionId}") {
        fun create(sessionId: String) = "change_setup/$sessionId"
    }
    data object DictTackle : SiedziRoute("dict_tackle")
    data object CatchPhoto : SiedziRoute("catch_photo/{sessionId}") {
        fun create(sessionId: String) = "catch_photo/$sessionId"
    }
    data object CatchCard : SiedziRoute("catch_card/{sessionId}") {
        fun create(sessionId: String) = "catch_card/$sessionId"
    }
}
